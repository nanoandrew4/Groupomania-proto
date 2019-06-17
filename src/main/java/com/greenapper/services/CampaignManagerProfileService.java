package com.greenapper.services;

import com.greenapper.models.CampaignManagerProfile;
import org.springframework.validation.BindingResult;

import java.util.Optional;

public interface CampaignManagerProfileService {
	void updateProfile(final CampaignManagerProfile updatedProfile, final BindingResult errors);

	Optional<CampaignManagerProfile> getProfileForCurrentUser();
}
