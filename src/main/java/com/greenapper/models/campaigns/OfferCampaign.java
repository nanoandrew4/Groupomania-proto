package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OfferCampaign")
public class OfferCampaign extends Campaign {
	public OfferCampaign() {
		this.setType(CampaignType.OFFER);
	}
}
