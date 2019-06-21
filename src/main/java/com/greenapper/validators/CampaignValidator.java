package com.greenapper.validators;

import com.greenapper.models.campaigns.Campaign;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

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

	public static void rejectIfNegative(final Double value, final String errorCode, final Errors errors) {
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

	public static void rejectDateIfEmpty(final LocalDate date, final String errorCode, final Errors errors) {
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
		rejectIfNegative(Double.valueOf(campaign.getQuantity()), "err.campaign.quantity", errors);
		rejectIfNull(campaign.isShowAfterExpiration(), "err.campaign.showAfterExpiration", errors);
		rejectIfNegative(campaign.getOriginalPrice(), "err.campaign.defaultPrice", errors);
		rejectDateIfEmpty(campaign.getStartDate(), "err.campaign.offer.startDate", errors);
		rejectDateIfEmpty(campaign.getEndDate(), "err.campaign.offer.endDate", errors);

		if ((campaign.getDiscountedPrice() == null || campaign.getDiscountedPrice() < 0) && (campaign.getPercentDiscount() == null || campaign.getPercentDiscount() < 0))
			errors.reject("err.campaign.discountedPriceOrPercent");
	}
}
