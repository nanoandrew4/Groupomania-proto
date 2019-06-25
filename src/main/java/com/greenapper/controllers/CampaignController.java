package com.greenapper.controllers;

import com.greenapper.enums.CampaignState;
import com.greenapper.enums.CampaignType;
import com.greenapper.forms.campaigns.CampaignForm;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.services.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class CampaignController {

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private ApplicationContext applicationContext;

	private Logger LOG = LoggerFactory.getLogger(CampaignController.class);

	private static final String ROOT_URI = "/campaigns";

	public final static String CAMPAIGN_VIEW_URI = ROOT_URI;

	public static final String CAMPAIGNS_OVERVIEW_URI = ROOT_URI;

	public static final String CAMPAIGN_CREATION_URI = ROOT_URI + "/create";

	public static final String CAMPAIGN_CREATION_DEFAULT_REDIRECTION = "redirect:" + CAMPAIGN_CREATION_URI + "?type=" + CampaignType.OFFER.displayName;

	public static final String CAMPAIGN_CREATION_SUCCESS_REDIRECT = "redirect:" + CampaignManagerController.CAMPAIGNS_OVERVIEW_URI;

	@GetMapping(CAMPAIGN_VIEW_URI + "/{id}")
	public String getCampaignById(final Model model, @PathVariable final Long id) {
		final CampaignForm campaignForm = createCampaignFormFromCampaign(campaignService.getCampaignById(id)).orElse(null);
		model.addAttribute("campaignForm", campaignForm);
		model.addAttribute("readonly", true);

		if (campaignForm != null)
			return getFormForCampaignType(campaignForm.getType().displayName);
		return getFormForCampaignType("offer");
	}

	@GetMapping(CAMPAIGN_CREATION_URI)
	public String getCampaignUpdateForm(final Model model, @RequestParam(required = false) final String type) {
		try {
			model.addAttribute("campaignForm", Class.forName(CampaignForm.class.getPackage().getName() + "." + type + "CampaignForm").getConstructor().newInstance());
			return getFormForCampaignType(type);
		} catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			if (type != null)
				LOG.error("Could not create Campaign model from type: \'" + type + "\', will use default redirect...");
		}

		return CAMPAIGN_CREATION_DEFAULT_REDIRECTION;
	}

	@PostMapping(CAMPAIGN_CREATION_URI)
	public String updateCampaign(final CampaignForm campaignForm, final Errors errors) {
		try {
			final CampaignService campaignService = (CampaignService) applicationContext.getBean(campaignForm.getType().displayName.toLowerCase() + "CampaignService");
			if (campaignForm.getId() == null)
				campaignService.createCampaign(campaignForm, errors);
			else
				campaignService.editCampaign(campaignForm, errors);

			if (!errors.hasErrors())
				return CAMPAIGN_CREATION_SUCCESS_REDIRECT;
		} catch (NoSuchBeanDefinitionException | NullPointerException e) {
			LOG.error("An error occurred while trying to get the service for the supplied campaign", e);
			errors.reject("err.campaign.type");
		}
		return getFormForCampaignType(campaignForm.getType().displayName);
	}

	@GetMapping(CAMPAIGNS_OVERVIEW_URI)
	public String getAllCampaigns(final Model model) {
		final List<Campaign> campaigns = campaignService.getAllCampaigns();

		campaigns.removeIf(campaign -> campaign.getState() == CampaignState.INACTIVE);
		campaigns.removeIf(campaign -> campaign.isShowAfterExpiration() && LocalDate.now().plus(4, ChronoUnit.DAYS).isAfter(campaign.getEndDate()));
		campaigns.removeIf(campaign -> !campaign.isShowAfterExpiration() && LocalDate.now().isAfter(campaign.getEndDate()));

		model.addAttribute("campaigns", campaigns);
		return "home";
	}

	public Optional<CampaignForm> createCampaignFormFromCampaign(final Campaign campaign) {
		try {
			final String fullClassName = CampaignForm.class.getPackage().getName() + "." + campaign.getType().displayName + "CampaignForm";
			final String modelClassName = Campaign.class.getPackage().getName() + "." + campaign.getType().displayName + "Campaign";
			return Optional.of((CampaignForm) Class.forName(fullClassName).getConstructor(Class.forName(modelClassName)).newInstance(campaign));
		} catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			LOG.error("Could not create campaign model for type: \'" + campaign.getType().displayName + "\'", e);
			return Optional.empty();
		}
	}

	public static String getFormForCampaignType(final String type) {
		return "campaigns/" + type.toLowerCase() + "Campaign";
	}
}
