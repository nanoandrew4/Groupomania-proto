package com.greenapper.validators;

import com.greenapper.models.campaigns.OfferCampaign;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class OfferCampaignValidator implements Validator {

	@Resource
	private Validator campaignValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return OfferCampaign.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final OfferCampaign offerCampaign = (OfferCampaign) target;

		campaignValidator.validate(offerCampaign, errors);
	}
}
