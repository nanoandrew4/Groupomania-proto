package com.greenapper.controllers;

import com.greenapper.enums.CampaignType;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;

@Controller
public class CampaignController {

	@Autowired
	private ApplicationContext applicationContext;

	private Logger LOG = LoggerFactory.getLogger(CampaignController.class);

	private static final String ROOT_URI = "/campaigns";

	public static final String CAMPAIGNS_OVERVIEW_URI = ROOT_URI + "/overview";

	public static final String CAMPAIGN_UPDATE_URI = ROOT_URI;

	public static final String CAMPAIGN_UPDATE_FORM = "campaigns/offerCampaign";

	public static final String CAMPAIGN_DEFAULT_REDIRECTION = "redirect:" + CAMPAIGN_UPDATE_URI + "?type=" + CampaignType.OFFER.displayName;

	public static final String CAMPAIGN_UPDATE_SUCCESS_REDIRECT = "redirect:" + CAMPAIGNS_OVERVIEW_URI;

	@GetMapping(CAMPAIGNS_OVERVIEW_URI)
	public String getCampaignManagerOverview() {
		return "campaigns/overview";
	}

	@GetMapping(CAMPAIGN_UPDATE_URI)
	public String getCampaignUpdateForm(final Model model, @RequestParam(required = false) final String type) {
		try {
			model.addAttribute("campaign", Class.forName(Campaign.class.getPackage().getName() + "." + type + "Campaign").getConstructor().newInstance());
			return "campaigns/" + type.toLowerCase() + "Campaign";
		} catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			if (type != null)
				LOG.error("Could not create Campaign model from type: \'" + type + "\', will use default redirect...");
		}

		return CAMPAIGN_DEFAULT_REDIRECTION;
	}

	@PostMapping(CAMPAIGN_UPDATE_URI)
	public String updateCampaign(final Campaign campaign, final Errors errors) {
		try {
			final CampaignService campaignService = (CampaignService) applicationContext.getBean(campaign.getType().displayName.toLowerCase() + "CampaignService");
			if (campaign.getId() == null)
				campaignService.createCampaign(campaign, errors);
			else
				campaignService.editCampaign(campaign, errors);

			if (!errors.hasErrors()) {
				return CAMPAIGN_UPDATE_SUCCESS_REDIRECT;
			}
		} catch (NoSuchBeanDefinitionException | NullPointerException e) {
			LOG.error("", e);
			errors.reject("err.campaign.type");
		}
		return CAMPAIGN_UPDATE_FORM;
	}
}
