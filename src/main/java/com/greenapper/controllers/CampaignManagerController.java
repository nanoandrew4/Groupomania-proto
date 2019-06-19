package com.greenapper.controllers;

import com.greenapper.models.PasswordUpdate;
import com.greenapper.services.CampaignManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
public class CampaignManagerController {

	private final static String ROOT_URI = "/campaign-manager";

	public final static String PASSWORD_UPDATE_URI = ROOT_URI + "/password-update";

	public final static String PASSWORD_UPDATE_FORM = "campaign_manager/passwordUpdate";

	public final static String PASSWORD_UPDATE_SUCCESS_REDIRECT = "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;

	@Autowired
	private CampaignManagerService campaignManagerService;

	@GetMapping(PASSWORD_UPDATE_URI)
	public String resetPassword(final PasswordUpdate passwordUpdate) {
		return PASSWORD_UPDATE_FORM;
	}

	@PatchMapping(PASSWORD_UPDATE_URI)
	public String updatePassword(final PasswordUpdate passwordUpdate, final Errors errors) {
		campaignManagerService.updatePassword(passwordUpdate, errors);

		if (!errors.hasErrors())
			return PASSWORD_UPDATE_SUCCESS_REDIRECT;
		else {
			return PASSWORD_UPDATE_FORM;
		}
	}
}
