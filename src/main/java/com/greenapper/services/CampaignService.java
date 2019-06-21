package com.greenapper.services;

import com.greenapper.models.campaigns.Campaign;
import org.springframework.validation.Errors;

import java.util.List;

public interface CampaignService {
	void createCampaign(final Campaign campaign, final Errors errors);

	void editCampaign(final Campaign campaign, final Errors errors);

	void validateCampaign(final Campaign campaign, final Errors errors);

	void setDefaultsForCampaignSubtype(final Campaign campaignSubtype);

	Campaign getCampaignById(final Long id);

	List<Campaign> getAllCampaignsForCurrentUser();

	boolean isCampaignArchived(final Long id);
}
