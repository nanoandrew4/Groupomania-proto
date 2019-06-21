package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CouponCampaign")
public class CouponCampaign extends Campaign {

	private String couponDescription;

	private String campaignManagerName;

	private String campaignManagerEmail;

	private String campaignManagerAddress;

	public CouponCampaign() {
		this.setType(CampaignType.COUPON);
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public String getCampaignManagerName() {
		return campaignManagerName;
	}

	public void setCampaignManagerName(String campaignManagerName) {
		this.campaignManagerName = campaignManagerName;
	}

	public String getCampaignManagerEmail() {
		return campaignManagerEmail;
	}

	public void setCampaignManagerEmail(String campaignManagerEmail) {
		this.campaignManagerEmail = campaignManagerEmail;
	}

	public String getCampaignManagerAddress() {
		return campaignManagerAddress;
	}

	public void setCampaignManagerAddress(String campaignManagerAddress) {
		this.campaignManagerAddress = campaignManagerAddress;
	}
}
