package com.greenapper.services;

import com.greenapper.models.Campaign;
import com.greenapper.models.CampaignManager;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface CampaignManagerService {
	Optional<CampaignManager> getByUsername(final String username);

	void updatePassword(final String oldPassword, final String newPassword, final BindingResult errors);

	void addCampaignToCurrentUser(final Campaign campaign);

	boolean isCurrentUserPasswordChangeRequired();
}