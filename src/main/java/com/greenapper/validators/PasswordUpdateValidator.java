package com.greenapper.validators;

import com.greenapper.config.SecurityConfig;
import com.greenapper.models.CampaignManager;
import com.greenapper.models.PasswordUpdate;
import com.greenapper.models.User;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordUpdateValidator implements Validator {

	@Autowired
	private SecurityConfig securityConfig;

	@Autowired
	private SessionService sessionService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CampaignManager.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final User sessionUser = sessionService.getSessionUser();
		final PasswordUpdate passwordUpdate = (PasswordUpdate) target;
		final PasswordEncoder pwdEncoder = securityConfig.getPasswordEncoder();

		if (pwdEncoder.matches(passwordUpdate.getNewPassword(), sessionUser.getPassword()))
			errors.reject("err.password.samepassword");
		else if (passwordUpdate.getNewPassword().length() < 6)
			errors.reject("err.password.length");
		else if (!pwdEncoder.matches(passwordUpdate.getOldPassword(), sessionUser.getPassword()))
			errors.reject("err.password.mismatch");
	}
}
