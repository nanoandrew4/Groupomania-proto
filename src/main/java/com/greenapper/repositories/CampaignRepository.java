package com.greenapper.repositories;

import com.greenapper.models.campaigns.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	boolean getCampaignArchivedById(final Long campaignId);
}
