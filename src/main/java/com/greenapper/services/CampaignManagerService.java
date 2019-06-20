package com.greenapper.services;

import com.greenapper.models.CampaignManager;
import com.greenapper.models.PasswordUpdate;
import com.greenapper.models.campaigns.Campaign;
import org.springframework.validation.Errors;

import java.util.Optional;

public interface CampaignManagerService {
	Optional<CampaignManager> getByUsername(final String username);

	void updatePassword(final PasswordUpdate passwordUpdate, final Errors errors);

	void addCampaignToCurrentUser(final Campaign campaign);

	boolean isCurrentUserPasswordChangeRequired();
}