package com.greenapper.services;

import com.greenapper.models.Campaign;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CampaignService {
	void createCampaign(final Campaign model, final BindingResult errors);

	void editCampaign(final Campaign model, final BindingResult errors);

	Campaign getCampaignById(final Long id);

	List<Campaign> getAllCampaignsForCurrentUser();

	boolean isCampaignArchived(final Long id);
}
