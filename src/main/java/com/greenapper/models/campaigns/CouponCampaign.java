package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignType;
import com.greenapper.models.CampaignManagerProfile;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CouponCampaign")
public class CouponCampaign extends Campaign {

	private String couponDescription;

	private CampaignManagerProfile contactInformation;

	public CouponCampaign() {
		this.setType(CampaignType.COUPON);
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public CampaignManagerProfile getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(CampaignManagerProfile contactInformation) {
		this.contactInformation = contactInformation;
	}
}
