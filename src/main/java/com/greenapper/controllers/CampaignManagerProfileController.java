package com.greenapper.controllers;

import com.greenapper.models.CampaignManagerProfile;
import com.greenapper.services.CampaignManagerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Controller
public class CampaignManagerProfileController {

	private final static String ROOT_URI = "/campaign-manager/profile";
	public final static String PROFILE_UPDATE_URI = ROOT_URI + "/setup";
	@Autowired
	private CampaignManagerProfileService campaignManagerProfileService;

	@GetMapping(PROFILE_UPDATE_URI)
	public String getCampaignManagerProfileSetup(final Model model) {
		final Optional<CampaignManagerProfile> profile = campaignManagerProfileService.getProfileForCurrentUser();
		model.addAttribute(profile.orElseGet(CampaignManagerProfile::new));
		return "campaign_manager/profileSetup";
	}

	@PutMapping(PROFILE_UPDATE_URI)
	public String updateProfile(final CampaignManagerProfile campaignManagerProfile, final BindingResult bindingResult) {
		campaignManagerProfileService.updateProfile(campaignManagerProfile, bindingResult);

		if (!bindingResult.hasErrors()) {
			return "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;
		} else {
			return "campaign_manager/profileSetup";
		}
	}
}
