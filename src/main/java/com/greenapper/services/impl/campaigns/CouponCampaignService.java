package com.greenapper.services.impl.campaigns;

import com.greenapper.models.CampaignManagerProfile;
import com.greenapper.models.campaigns.Campaign;
import com.greenapper.models.campaigns.CouponCampaign;
import com.greenapper.services.CampaignManagerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Service
public class CouponCampaignService extends DefaultCampaignService {

	@Resource
	private Validator couponCampaignValidator;

	@Resource
	private CampaignManagerProfileService campaignManagerProfileService;

	@Override
	public void validateCampaign(final Campaign campaign, final Errors errors) {
		couponCampaignValidator.validate(campaign, errors);
	}

	@Override
	public void setDefaultsForCampaignSubtype(final Campaign campaignSubtype) {
		final CouponCampaign couponCampaign = (CouponCampaign) campaignSubtype;
		final CampaignManagerProfile sessionProfile = campaignManagerProfileService.getProfileForCurrentUser().orElseThrow(IllegalArgumentException::new);

		if (couponCampaign.getCampaignManagerName() == null || couponCampaign.getCampaignManagerName().trim().isEmpty())
			couponCampaign.setCampaignManagerName(sessionProfile.getName());
		if (couponCampaign.getCampaignManagerEmail() == null || couponCampaign.getCampaignManagerEmail().trim().isEmpty())
			couponCampaign.setCampaignManagerEmail(sessionProfile.getEmail());
		if (couponCampaign.getCampaignManagerAddress() == null || couponCampaign.getCampaignManagerAddress().trim().isEmpty())
			couponCampaign.setCampaignManagerAddress(sessionProfile.getAddress());
	}
}