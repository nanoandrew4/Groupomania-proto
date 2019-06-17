package com.greenapper.repositories;

import com.greenapper.models.CampaignManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignManagerRepository extends JpaRepository<CampaignManager, Long> {
	CampaignManager findByUsername(final String username);

	boolean findPasswordChangeRequiredById(final Long userId);
}
