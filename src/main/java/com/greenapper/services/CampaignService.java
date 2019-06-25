package com.greenapper.services;

import com.greenapper.forms.campaigns.CampaignForm;
import com.greenapper.models.campaigns.Campaign;
import org.springframework.validation.Errors;

import java.util.List;

public interface CampaignService {
	void createCampaign(final CampaignForm campaign, final Errors errors);

	void editCampaign(final CampaignForm campaign, final Errors errors);

	void updateCampaignState(final Long id, final String state);

	void validateCampaign(final CampaignForm campaign, final Errors errors);

	void setDefaultsForCampaignSubtype(final Campaign campaignSubtype);

	Campaign getCampaignById(final Long id);

	Campaign getCampaignByIdAndSessionUser(final Long id);

	List<Campaign> getAllCampaignsForCurrentUser();

	List<Campaign> getAllCampaigns();

	boolean isCampaignArchived(final Long id);
}
