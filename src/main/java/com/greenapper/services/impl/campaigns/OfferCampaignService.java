package com.greenapper.services.impl.campaigns;

import com.greenapper.models.campaigns.Campaign;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Service
public class OfferCampaignService extends DefaultCampaignService {

	@Resource
	private Validator offerCampaignValidator;

	@Override
	public void validateCampaign(final Campaign campaign, final Errors errors) {
		offerCampaignValidator.validate(campaign, errors);
	}

	@Override
	public void setDefaultsForCampaignSubtype(final Campaign campaignSubtype) {
	}
}
