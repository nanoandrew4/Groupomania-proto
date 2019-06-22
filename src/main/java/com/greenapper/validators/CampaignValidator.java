package com.greenapper.validators;

import com.greenapper.models.campaigns.Campaign;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CampaignValidator implements Validator {

	private static final Long MAX_STRING_LENGTH = 255L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Campaign.class.equals(clazz);
	}

	public static void rejectIfNull(final Object value, final String errorCode, final Errors errors) {
		if (value == null)
			errors.reject(errorCode);
	}

	public static void rejectIfNumberNullOrNegative(final Long value, final String errorCode, final Errors errors) {
		if (value == null || value < 0)
			errors.reject(errorCode);
	}

	public static void rejectIfNumberNullOrNegative(final Double value, final String errorCode, final Errors errors) {
		if (value == null || value < 0)
			errors.reject(errorCode);
	}

	public static void rejectStringIfPresentAndTooLong(final String value, final String errorCode, final Errors errors) {
		if (value != null && value.length() > MAX_STRING_LENGTH)
			errors.reject(errorCode);
	}

	public static void rejectStringIfEmptyOrTooLong(final String value, final String errorCode, final Errors errors) {
		if (value == null || value.trim().isEmpty() || value.length() > MAX_STRING_LENGTH)
			errors.reject(errorCode);
	}

	public static void rejectDateIfEmptyOrBeforeNow(final LocalDate date, final String errorCode, final Errors errors) {
		if (date == null || date.isBefore(LocalDate.now()))
			errors.reject(errorCode);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			errors.reject("err.campaign");
			return;
		}

		final Campaign campaign = (Campaign) target;

		rejectStringIfEmptyOrTooLong(campaign.getTitle(), "err.campaign.title", errors);
		rejectStringIfEmptyOrTooLong(campaign.getDescription(), "err.campaign.description", errors);
		rejectStringIfEmptyOrTooLong(campaign.getType().toString(), "err.campaign.type", errors);
		rejectIfNull(campaign.getOwner(), "err.campaign.owner", errors);
		rejectIfNumberNullOrNegative(campaign.getQuantity(), "err.campaign.quantity", errors);
		rejectIfNumberNullOrNegative(campaign.getOriginalPrice(), "err.campaign.originalPrice", errors);
		rejectDateIfEmptyOrBeforeNow(campaign.getStartDate(), "err.campaign.startDate", errors);
		rejectDateIfEmptyOrBeforeNow(campaign.getEndDate(), "err.campaign.endDate", errors);

		if (campaign.getCampaignImage() != null && (campaign.getCampaignImage().getContentType() == null || !campaign.getCampaignImage().getContentType().contains("image")))
			errors.reject("err.campaign.imageFormat");

		if (campaign.getStartDate() != null && campaign.getEndDate() != null && campaign.getStartDate().isAfter(campaign.getEndDate().minus(1, ChronoUnit.DAYS)))
			errors.reject("err.campaign.startDateAfterEndDate");

		if ((campaign.getDiscountedPrice() == null || campaign.getDiscountedPrice() < 0) && (campaign.getPercentDiscount() == null || campaign.getPercentDiscount() < 0))
			errors.reject("err.campaign.discountedPriceOrPercent");
	}
}
