package com.greenapper.forms.campaigns;

import com.greenapper.enums.CampaignType;
import com.greenapper.models.campaigns.OfferCampaign;

public class OfferCampaignForm extends CampaignForm {
	public OfferCampaignForm() {
		this.setType(CampaignType.OFFER);
	}

	public OfferCampaignForm(final OfferCampaign offerCampaign) {
		super(offerCampaign);
	}
}
