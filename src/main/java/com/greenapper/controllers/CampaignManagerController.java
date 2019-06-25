package com.greenapper.controllers;

import com.greenapper.forms.campaigns.CampaignForm;
import com.greenapper.models.PasswordUpdate;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.InvocationTargetException;
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

	public final static String CAMPAIGNS_OVERVIEW_FORM = "campaign_manager/campaignsOverview";

	public final static String PASSWORD_UPDATE_SUCCESS_REDIRECT = "redirect:" + CampaignController.CAMPAIGNS_OVERVIEW_URI;

	private Logger LOG = LoggerFactory.getLogger(CampaignManagerController.class);

	@Autowired
	private CampaignService campaignService;

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

	@GetMapping(CAMPAIGN_UPDATE_URI)
	public String getCampaignForEditById(final Model model, @PathVariable final Long id) {
		final Predicate<Campaign> findById = campaign -> campaign.getId().equals(id);
		final Campaign campaign = campaignManagerService.getCampaigns().stream().filter(findById).findFirst().orElse(null);

		if (campaign != null) {
			try {
				final Class<?> campaignModel = Class.forName(Campaign.class.getPackage().getName() + "." + campaign.getType().displayName + "Campaign");
				final CampaignForm campaignForm = (CampaignForm) Class.forName(CampaignForm.class.getPackage().getName() + "." + campaign.getType().displayName + "CampaignForm").getConstructor(campaignModel).newInstance(campaign);
				model.addAttribute("campaignForm", campaignForm);
				return CampaignController.getFormForCampaignType(campaign.getType().displayName);
			} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				LOG.error("Error creating campaign form for type: \'" + campaign.getType().displayName + "\'", e);
			}
		}

		return CampaignController.CAMPAIGN_CREATION_DEFAULT_REDIRECTION;
	}

	@PatchMapping(CAMPAIGN_STATE_UPDATE_URI)
	public String updateCampaignState(@PathVariable final Long id, @PathVariable final String state) {
		campaignService.updateCampaignState(id, state);
		return CAMPAIGN_STATE_UPDATE_SUCCESS_REDIRECT;
	}
}
