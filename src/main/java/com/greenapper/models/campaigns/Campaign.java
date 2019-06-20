package com.greenapper.models.campaigns;

import com.greenapper.enums.CampaignState;
import com.greenapper.enums.CampaignType;
import com.greenapper.models.CampaignManager;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(
		strategy = InheritanceType.JOINED
)
public class Campaign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private CampaignManager owner;

	private String title;

	private String description;

	@Lob
	private MultipartFile productImg;

	private CampaignType type;

	private LocalDate startDate;

	private LocalDate endDate;

	private Long quantity;

	private boolean showAfterExpiration;

	private Double originalPrice;

	private double percentDiscount;

	private double discountedPrice;

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

	public MultipartFile getProductImg() {
		return productImg;
	}

	public void setProductImg(MultipartFile productImg) {
		this.productImg = productImg;
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
