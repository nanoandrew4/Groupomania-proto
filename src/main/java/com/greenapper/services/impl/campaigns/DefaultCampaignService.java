package com.greenapper.services.impl.campaigns;

import com.greenapper.enums.CampaignState;
import com.greenapper.models.CampaignManager;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.repositories.CampaignRepository;
import com.greenapper.services.CampaignManagerService;
import com.greenapper.services.CampaignService;
import com.greenapper.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class DefaultCampaignService implements CampaignService {

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CampaignManagerService userService;

	@Autowired
	private SessionService sessionService;

	@Value("${groupomania.filestorage.rootdir}")
	private String rootStorageDir;

	private MessageDigest md;

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	public DefaultCampaignService() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Could not instantiate message digest for hashing filenames");
		}
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
		if (campaign.getCampaignImage() != null) {
			final String storedUrl = persistImageToFileSystem(campaign.getCampaignImage());
			campaign.setCampaignImageUrl(storedUrl);
		}

		campaignRepository.save(campaign);
	}

	private String persistImageToFileSystem(final MultipartFile image) {
		final String contentType = Objects.requireNonNull(image.getContentType()).replace("image/", "");
		try {
			final String hashedFileName = new String(Base64.getEncoder().encode(md.digest(image.getBytes())));
			final String userNameHash = new String(Base64.getEncoder().encode(md.digest(sessionService.getSessionUser().getUsername().getBytes())));
			final String relativeStoragePath = userNameHash + "/" + hashedFileName + "." + contentType;
			final File outputFile = new File(rootStorageDir + relativeStoragePath);

			Files.createDirectories(Paths.get(outputFile.getAbsolutePath()));

			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image.getBytes());
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			ImageIO.write(bufferedImage, contentType, outputFile);
			return relativeStoragePath;
		} catch (IOException e) {
			LOG.error("Reading bytes from image with name + \'" + image.getName() + "\' and user \'" + sessionService.getSessionUser().getUsername() + "\' failed");
		}

		return null;
	}

	private CampaignManager getSessionCampaignManager() {
		return (CampaignManager) sessionService.getSessionUser();
	}
}
