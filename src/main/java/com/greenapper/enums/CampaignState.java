package com.greenapper.enums;

public enum CampaignState {
	ACTIVE("Active"), INACTIVE("Inactive"), ARCHIVED("Archived");

	public final String displayName;

	CampaignState(final String displayName) {
		this.displayName = displayName;
	}
}
