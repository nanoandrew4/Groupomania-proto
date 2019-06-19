package com.greenapper.controllers;

import com.greenapper.models.CampaignManagerProfile;
import com.greenapper.services.CampaignManagerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Controller
public class CampaignManagerProfileController {

	@Autowired
	private CampaignManagerProfileService campaignManagerProfileService;

	private final static String ROOT_URI = "/campaign-manager/profile";

	public final static String PROFILE_UPDATE_URI = ROOT_URI + "/setup";

	public final static String PROFILE_UPDATE_FORM = "campaign_manager/profileSetup";

	public final static String PROFILE_UPDATE_SUCCESS = "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;

	@GetMapping(PROFILE_UPDATE_URI)
	public String getCampaignManagerProfileSetup(final Model model) {
		final Optional<CampaignManagerProfile> profile = campaignManagerProfileService.getProfileForCurrentUser();
		model.addAttribute(profile.orElseGet(CampaignManagerProfile::new));
		return PROFILE_UPDATE_FORM;
	}

	@PutMapping(PROFILE_UPDATE_URI)
	public String updateProfile(final CampaignManagerProfile campaignManagerProfile, final Errors errors) {
		campaignManagerProfileService.updateProfile(campaignManagerProfile, errors);

		if (!errors.hasErrors()) {
			return PROFILE_UPDATE_SUCCESS;
		} else {
			return PROFILE_UPDATE_FORM;
		}
	}
}
