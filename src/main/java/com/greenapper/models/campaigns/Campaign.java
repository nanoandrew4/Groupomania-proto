package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignState;
import com.greenapper.enums.CampaignType;
import com.greenapper.models.CampaignManager;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(
		strategy = InheritanceType.JOINED
)
public abstract class Campaign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private CampaignManager owner;

	private String title;

	private String description;

	@Transient
	private MultipartFile campaignImage;

	@Transient
	private String encodedCampaignImage;

	private String campaignImageFileName;

	private CampaignType type;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate endDate;

	private Long quantity;

	private boolean showAfterExpiration;

	private Double originalPrice;

	private Double percentDiscount;

	private Double discountedPrice;

	private CampaignState state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CampaignManager getOwner() {
		return owner;
	}

	public void setOwner(CampaignManager owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getCampaignImage() {
		return campaignImage;
	}

	public void setCampaignImage(MultipartFile campaignImage) {
		this.campaignImage = campaignImage;
	}

	public CampaignType getType() {
		return type;
	}

	public void setType(CampaignType type) {
		this.type = type;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public boolean isShowAfterExpiration() {
		return showAfterExpiration;
	}

	public void setShowAfterExpiration(boolean showAfterExpiration) {
		this.showAfterExpiration = showAfterExpiration;
	}

	public CampaignState getState() {
		return state;
	}

	public void setState(CampaignState state) {
		this.state = state;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(Double percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public Double getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(Double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getCampaignImageFileName() {
		return campaignImageFileName;
	}

	public void setCampaignImageFileName(String campaignImageFileName) {
		this.campaignImageFileName = campaignImageFileName;
	}
}
