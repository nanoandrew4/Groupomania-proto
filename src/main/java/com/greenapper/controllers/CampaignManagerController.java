package com.greenapper.controllers;

import com.greenapper.enums.CampaignState;
import com.greenapper.models.PasswordUpdate;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.function.Predicate;

@Controller
public class CampaignManagerController {

	private final static String ROOT_URI = "/campaign-manager";

	public final static String PASSWORD_UPDATE_URI = ROOT_URI + "/password/update";

	public final static String PASSWORD_UPDATE_FORM = "campaign_manager/passwordUpdate";

	public final static String CAMPAIGNS_OVERVIEW_URI = ROOT_URI + "/campaigns";

	public final static String CAMPAIGN_UPDATE_URI = ROOT_URI + "/campaigns/update/{id}";

	public final static String CAMPAIGN_STATE_UPDATE_URI = ROOT_URI + "/campaigns/update/state/{id}/{state}";

	public final static String CAMPAIGN_STATE_UPDATE_SUCCESS_REDIRECT = "redirect:" + CAMPAIGNS_OVERVIEW_URI;

	public final static String CAMPAIGN_VIEW_URI = ROOT_URI + "/campaigns/{id}";

	public final static String CAMPAIGNS_OVERVIEW_FORM = "campaign_manager/campaignsOverview";

	public final static String PASSWORD_UPDATE_SUCCESS_REDIRECT = "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;

	@Autowired
	private ApplicationContext applicationContext;

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

	@GetMapping(CAMPAIGNS_OVERVIEW_URI)
	public String getCampaignOverview(final Model model) {
		model.addAttribute("campaigns", campaignManagerService.getCampaigns());
		return CAMPAIGNS_OVERVIEW_FORM;
	}

	@GetMapping(CAMPAIGN_VIEW_URI)
	public String getCampaignById(final Model model, @PathVariable final Long id) {
		final String form = getCampaignForEditById(model, id);
		model.addAttribute("readonly", true);
		return form;
	}

	@GetMapping(CAMPAIGN_UPDATE_URI)
	public String getCampaignForEditById(final Model model, @PathVariable final Long id) {
		final Predicate<Campaign> findById = campaign -> campaign.getId().equals(id);
		final Campaign campaign = campaignManagerService.getCampaigns().stream().filter(findById).findFirst().orElse(null);

		if (campaign != null) {
			model.addAttribute("campaign", campaign);
			return CampaignController.getFormForCampaignType(campaign.getType().displayName);
		}

		return CampaignController.CAMPAIGN_CREATION_DEFAULT_REDIRECTION;
	}

	@PatchMapping(CAMPAIGN_STATE_UPDATE_URI)
	public String updateCampaignState(@PathVariable final Long id, @PathVariable final String state) {
		final Predicate<Campaign> findById = campaign -> campaign.getId().equals(id);
		final Campaign campaign = campaignManagerService.getCampaigns().stream().filter(findById).findFirst().orElse(null);

		if (campaign != null) {
			campaign.setState(CampaignState.valueOf(state.toUpperCase()));
			final CampaignService campaignService = (CampaignService) applicationContext.getBean(campaign.getType().displayName.toLowerCase() + "CampaignService");
			campaignService.editCampaign(campaign, new BeanPropertyBindingResult(campaign, "campaign"));
		}

		return CAMPAIGN_STATE_UPDATE_SUCCESS_REDIRECT;
	}
}
