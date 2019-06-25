package com.greenapper.services.impl.campaigns;

import com.greenapper.enums.CampaignState;
import com.greenapper.forms.campaigns.CampaignForm;
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

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public abstract class DefaultCampaignService implements CampaignService {

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CampaignManagerService campaignManagerService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private FileSystemStorageService fileSystemStorageService;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	public DefaultCampaignService() {

	}

	@Override
	public void createCampaign(final CampaignForm campaignForm, final Errors errors) {
		validateCampaign(campaignForm, errors);
		if (!errors.hasErrors()) {
			createCampaignFromForm(campaignForm).ifPresent(campaign -> {
				campaign.setOwner(getSessionCampaignManager());
				campaign.setState(CampaignState.INACTIVE);
				setDefaultsForCampaignSubtype(campaign);
				saveCampaign(campaign, campaignForm);
			});
		}
	}

	@Override
	public void editCampaign(final CampaignForm campaignForm, final Errors errors) {
		validateCampaign(campaignForm, errors);
		if (!errors.hasErrors()) {
			createCampaignFromForm(campaignForm).ifPresent(campaign -> {
				campaign.setOwner(getSessionCampaignManager());
				saveCampaign(campaign, campaignForm);
			});
		}
	}

	@Override
	public void updateCampaignState(final Long id, final String state) {
		campaignRepository.findById(id).ifPresent(campaign -> {
			campaign.setState(CampaignState.valueOf(state.toUpperCase()));
			campaignManagerService.addCampaignToCurrentUser(campaign);
		});
	}

	@Override
	public Campaign getCampaignById(final Long id) {
		return campaignRepository.findById(id).orElse(null);
	}

	@Override
	public Campaign getCampaignByIdAndSessionUser(final Long id) {
		final Optional<Campaign> campaign = campaignRepository.findById(id);
		if (campaign.isPresent() && campaign.get().getOwner().getId().equals(sessionService.getSessionUser().getId()))
			return campaign.get();
		return null;
	}

	@Override
	public List<Campaign> getAllCampaigns() {
		return campaignRepository.findAll();
	}

	@Override
	public List<Campaign> getAllCampaignsForCurrentUser() {
		return getSessionCampaignManager().getCampaigns();
	}

	@Override
	public boolean isCampaignArchived(final Long id) {
		return campaignRepository.getCampaignArchivedById(id);
	}

	public Optional<Campaign> createCampaignFromForm(final CampaignForm campaignForm) {
		try {
			final String fullClassName = Campaign.class.getPackage().getName() + "." + campaignForm.getType().displayName + "Campaign";
			final String formClassName = CampaignForm.class.getPackage().getName() + "." + campaignForm.getType().displayName + "CampaignForm";
			return Optional.of((Campaign) Class.forName(fullClassName).getConstructor(Class.forName(formClassName)).newInstance(campaignForm));
		} catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			LOG.error("Could not create campaign model for type: \'" + campaignForm.getType().displayName + "\'", e);
			return Optional.empty();
		}
	}

	private void saveCampaign(final Campaign campaign, final CampaignForm campaignForm) {
		campaign.setCampaignImageFileName(fileSystemStorageService.saveImage(campaignForm.getCampaignImage(), campaignForm.getCampaignImageFileName()));

		campaignRepository.save(campaign);
		campaignManagerService.addCampaignToCurrentUser(campaign);
	}

	private CampaignManager getSessionCampaignManager() {
		return (CampaignManager) sessionService.getSessionUser();
	}
}
