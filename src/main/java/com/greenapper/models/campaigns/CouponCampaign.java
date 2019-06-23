package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "CouponCampaign")
public class CouponCampaign extends Campaign {

	private String couponDescription;

	private String campaignManagerName;

	private String campaignManagerEmail;

	private String campaignManagerAddress;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate couponStartDate;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate couponEndDate;

	public CouponCampaign() {
		this.setType(CampaignType.COUPON);
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public String getCampaignManagerName() {
		return campaignManagerName;
	}

	public void setCampaignManagerName(String campaignManagerName) {
		this.campaignManagerName = campaignManagerName;
	}

	public String getCampaignManagerEmail() {
		return campaignManagerEmail;
	}

	public void setCampaignManagerEmail(String campaignManagerEmail) {
		this.campaignManagerEmail = campaignManagerEmail;
	}

	public String getCampaignManagerAddress() {
		return campaignManagerAddress;
	}

	public void setCampaignManagerAddress(String campaignManagerAddress) {
		this.campaignManagerAddress = campaignManagerAddress;
	}

	public LocalDate getCouponStartDate() {
		return couponStartDate;
	}

	public void setCouponStartDate(LocalDate couponStartDate) {
		this.couponStartDate = couponStartDate;
	}

	public LocalDate getCouponEndDate() {
		return couponEndDate;
	}

	public void setCouponEndDate(LocalDate couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
}
