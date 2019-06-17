package com.greenapper.models;

import com.greenapper.enums.CampaignType;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OfferCampaign")
public class OfferCampaign extends Campaign {
	private double originalPrice;
	private double percentDiscount;
	private double discountedPrice;

	public OfferCampaign() {
		this.setType(CampaignType.OFFER);
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(double percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
}
