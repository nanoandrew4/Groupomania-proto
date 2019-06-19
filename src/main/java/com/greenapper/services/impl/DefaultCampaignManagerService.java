package com.greenapper.services.impl;

import com.greenapper.config.SecurityConfig;
import com.greenapper.models.Campaign;
import com.greenapper.models.CampaignManager;
import com.greenapper.repositories.CampaignManagerRepository;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class DefaultCampaignManagerService implements CampaignManagerService {

	@Autowired
	private CampaignManagerRepository campaignManagerRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private SecurityConfig securityConfig;

	@Override
	public Optional<CampaignManager> getByUsername(final String username) {
		return Optional.ofNullable(campaignManagerRepository.findByUsername(username));
	}

	@Override
	public void updatePassword(final String oldPassword, final String newPassword, final BindingResult errors) {
		final CampaignManager sessionUser = getSessionCampaignManager();
		final PasswordEncoder pwdEncoder = securityConfig.passwordEncoder();

		if (newPassword.equals(oldPassword))
			errors.reject("err.password.samepassword");
		else if (newPassword.length() < 6)
			errors.reject("err.password.length");
		else if (!pwdEncoder.matches(oldPassword, sessionUser.getPassword()))
			errors.reject("err.password.mismatch");

		if (!errors.hasErrors()) {
			sessionUser.setPassword(pwdEncoder.encode(newPassword));
			sessionUser.setPasswordChangeRequired(false);
			campaignManagerRepository.save(sessionUser);
		}

		sessionService.setSessionUser(sessionUser);
	}

	@Override
	public void addCampaignToCurrentUser(final Campaign campaign) {
		final CampaignManager campaignManager = getSessionCampaignManager();
		campaignManager.getCampaigns().add(campaign);
		campaignManagerRepository.save(campaignManager);
	}

	@Override
	public boolean isCurrentUserPasswordChangeRequired() {
		return campaignManagerRepository.findPasswordChangeRequiredById(sessionService.getSessionUser().getId());
	}

	private CampaignManager getSessionCampaignManager() {
		return (CampaignManager) sessionService.getSessionUser();
	}
}
