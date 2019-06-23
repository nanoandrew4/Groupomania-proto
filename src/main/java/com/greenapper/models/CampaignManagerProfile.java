package com.greenapper.models;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity(name = "CampaignManagerProfile")
@Table(name = "CampaignManagerProfile")
public class CampaignManagerProfile {
	@Id
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private CampaignManager campaignManager;

	private String name;

	private String email;

	private String address;

	@Transient
	private MultipartFile profileImage;

	private String profileImageFileName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CampaignManager getCampaignManager() {
		return campaignManager;
	}

	public void setCampaignManager(CampaignManager campaignManager) {
		this.campaignManager = campaignManager;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MultipartFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileImageFileName() {
		return profileImageFileName;
	}

	public void setProfileImageFileName(String profileImageFileName) {
		this.profileImageFileName = profileImageFileName;
	}
}
