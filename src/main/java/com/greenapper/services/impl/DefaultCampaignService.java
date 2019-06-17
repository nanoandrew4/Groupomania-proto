package com.greenapper.services.impl;

import com.greenapper.models.Campaign;
import com.greenapper.models.CampaignManager;
import com.greenapper.repositories.CampaignRepository;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.CampaignService;
import com.greenapper.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultCampaignService implements CampaignService {

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CampaignManagerService userService;

	@Autowired
	private SessionService sessionService;

	@Override
	public void createCampaign(final Campaign model, final BindingResult errors) {
		validateCampaign(model, errors);
		if (!errors.hasErrors()) {
			model.setOwner(getSessionCampaignManager());
			campaignRepository.save(model);
			userService.addCampaignToCurrentUser(model);
		}
	}

	@Override
	public void editCampaign(final Campaign model, final BindingResult errors) {
		validateCampaign(model, errors);
		if (!errors.hasErrors())
			campaignRepository.save(model);
	}

	@Override
	public Campaign getCampaignById(final Long id) {
		final Optional<Campaign> campaign = campaignRepository.findById(id);
		if (campaign.isPresent() && campaign.get().getOwner().getId().equals(sessionService.getSessionUser().getId()))
			return campaign.get();
		return null;
	}

	@Override
	public List<Campaign> getAllCampaignsForCurrentUser() {
		return getSessionCampaignManager().getCampaigns();
	}

	@Override
	public boolean isCampaignArchived(final Long id) {
		return campaignRepository.getCampaignArchivedById(id);
	}

	private void validateCampaign(final Campaign campaign, final BindingResult errors) {

	}

	private CampaignManager getSessionCampaignManager() {
		return (CampaignManager) sessionService.getSessionUser();
	}
}
