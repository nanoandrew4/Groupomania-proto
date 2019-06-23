package com.greenapper.services.impl.campaigns;

import com.greenapper.enums.CampaignState;
import com.greenapper.models.CampaignManager;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.repositories.CampaignRepository;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.CampaignService;
import com.greenapper.services.FileSystemStorageService;
import com.greenapper.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

public abstract class DefaultCampaignService implements CampaignService {

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CampaignManagerService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private FileSystemStorageService fileSystemStorageService;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	public DefaultCampaignService() {

	}

	@Override
	public void createCampaign(final Campaign campaign, final Errors errors) {
		campaign.setOwner(getSessionCampaignManager());
		campaign.setState(CampaignState.INACTIVE);

		setDefaultsForCampaignSubtype(campaign);

		validateCampaign(campaign, errors);
		if (!errors.hasErrors())
			saveCampaign(campaign);
	}

	@Override
	public void editCampaign(final Campaign campaign, final Errors errors) {
		validateCampaign(campaign, errors);
		if (!errors.hasErrors())
			saveCampaign(campaign);
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

	private void saveCampaign(final Campaign campaign) {
		if (campaign.getCampaignImage() != null)
			campaign.setCampaignImageFileName(fileSystemStorageService.saveImage(campaign.getCampaignImage()));

		campaignRepository.save(campaign);
	}

	private CampaignManager getSessionCampaignManager() {
		return (CampaignManager) sessionService.getSessionUser();
	}
}
