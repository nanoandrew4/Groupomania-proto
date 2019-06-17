package com.greenapper.models;

import com.greenapper.enums.CampaignType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CouponCampaign")
public class CouponCampaign extends OfferCampaign {

	private String couponDescription;

	public CouponCampaign() {
		this.setType(CampaignType.COUPON);
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}
}
