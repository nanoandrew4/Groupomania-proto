package com.greenapper.services;

import com.greenapper.models.CampaignManagerProfile;
import org.springframework.validation.Errors;

import java.util.Optional;

public interface CampaignManagerProfileService {
	void updateProfile(final CampaignManagerProfile updatedProfile, final Errors errors);

	Optional<CampaignManagerProfile> getProfileForCurrentUser();
}
