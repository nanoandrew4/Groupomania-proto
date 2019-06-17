package com.greenapper.controllers;

import com.greenapper.models.User;
import com.greenapper.services.CampaignManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
public class CampaignManagerController {

	public final static String ROOT_URI = "/campaign-manager";

	public final static String PASSWORD_UPDATE_URI = ROOT_URI + "/password-update";

	public final static String PASSWORD_UPDATE_FORM = "campaign_manager/passwordUpdate";

	public final static String PASSWORD_UPDATE_SUCCESS_REDIRECT = "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;

	@Autowired
	private CampaignManagerService campaignManagerService;

	@GetMapping(PASSWORD_UPDATE_URI)
	public String resetPassword(final User user) {
		return PASSWORD_UPDATE_FORM;
	}

	@PatchMapping(PASSWORD_UPDATE_URI)
	public String updatePassword(final User user, final BindingResult bindingResult) {
		campaignManagerService.updatePassword(user.getPassword(), bindingResult);

		if (!bindingResult.hasErrors())
			return PASSWORD_UPDATE_SUCCESS_REDIRECT;
		else {
			return PASSWORD_UPDATE_FORM;
		}
	}
}
