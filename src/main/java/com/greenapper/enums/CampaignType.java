package com.greenapper.enums;

public enum CampaignType {
	COUPON("Coupon"), OFFER("Offer");

	public final String displayName;

	CampaignType(final String displayName) {
		this.displayName = displayName;
	}
}
