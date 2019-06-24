package com.greenapper.validators;

import com.greenapper.forms.campaigns.CouponCampaignForm;
import com.greenapper.models.campaigns.CouponCampaign;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Component
public class CouponCampaignValidator implements Validator {

	@Resource
	private Validator campaignValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return CouponCampaign.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		campaignValidator.validate(target, errors);

		final CouponCampaignForm couponCampaignForm = (CouponCampaignForm) target;
		CampaignValidator.rejectStringIfPresentAndTooLong(couponCampaignForm.getCouponDescription(), "err.campaign.coupon.description", errors);
		CampaignValidator.rejectStringIfPresentAndTooLong(couponCampaignForm.getCampaignManagerName(), "err.campaign.coupon.managerName", errors);
		CampaignValidator.rejectStringIfPresentAndTooLong(couponCampaignForm.getCampaignManagerEmail(), "err.campaign.coupon.managerEmail", errors);
		CampaignValidator.rejectStringIfPresentAndTooLong(couponCampaignForm.getCampaignManagerAddress(), "err.campaign.coupon.managerAddress", errors);
		CampaignValidator.rejectDateIfEmptyOrBeforeNow(couponCampaignForm.getCouponStartDate(), "err.campaign.coupon.couponStartDate", errors);
		CampaignValidator.rejectDateIfEmptyOrBeforeNow(couponCampaignForm.getCouponEndDate(), "err.campaign.coupon.couponEndDate", errors);

		CampaignValidator.rejectDateIfEqualOrAfterOtherDate(couponCampaignForm.getCouponStartDate(), couponCampaignForm.getCouponEndDate(), "err.campaign.coupon.startDateAfterEndDate", errors);
		CampaignValidator.rejectDateIfBeforeOtherDate(couponCampaignForm.getCouponStartDate(), couponCampaignForm.getStartDate(), "err.campaign.coupon.couponStartDateBeforeCampaign", errors);
		CampaignValidator.rejectDateIfAfterOtherDate(couponCampaignForm.getCouponEndDate(), couponCampaignForm.getEndDate(), "err.campaign.coupon.couponEndDateBeforeCampaign", errors);

		if (couponCampaignForm.getDiscountedPrice() == null && couponCampaignForm.getPercentDiscount() == null && (couponCampaignForm.getCouponDescription() == null || couponCampaignForm.getCouponDescription().trim().isEmpty()))
			errors.reject("err.campaign.coupon.discountedPriceOrPercentOrDescription");
	}
}
