package com.greenapper.controllers;

import com.greenapper.models.Campaign;
import com.greenapper.models.OfferCampaign;
import com.greenapper.services.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;

@Controller
public class CampaignController {

	private static final String ROOT_URI = "/campaigns";
	public static final String CAMPAIGNS_OVERVIEW_URI = ROOT_URI + "/overview";
	public static final String CAMPAIGN_UPDATE_URI = ROOT_URI;
	@Autowired
	private CampaignService campaignService;
	private Logger LOG = LoggerFactory.getLogger(CampaignController.class);

	@GetMapping(CAMPAIGNS_OVERVIEW_URI)
	public String getCampaignManagerOverview() {
		return "campaigns/overview";
	}

	@GetMapping(CAMPAIGN_UPDATE_URI)
	public String getOfferCampaignUpdatePage(final Model model, @RequestParam(required = false) final String type) {
		try {
			model.addAttribute("campaign", Class.forName("com.greenapper.models." + type + "Campaign").getConstructor().newInstance());
			return "campaigns/" + type.toLowerCase() + "Campaign";
		} catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			if (type != null)
				LOG.error(e.getMessage(), e);
		}

		model.addAttribute("campaign", new OfferCampaign());
		return "campaigns/offerCampaign";
	}

	@PostMapping(CAMPAIGN_UPDATE_URI)
	public String updateCampaign(final Campaign campaign, final BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			return "redirect:" + CAMPAIGNS_OVERVIEW_URI;
		}
		return null;
	}
}
