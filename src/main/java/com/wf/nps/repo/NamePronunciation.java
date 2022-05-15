package com.wf.nps.repo;

import java.util.Date;

public class NamePronunciation {

	private Long id;
	private String uId;
	private String legalFName;
	private String legalLName;
	private String prefName;
	private byte[] prefPronunciation;
	private String createdBy;
	private Date createdOn;
	private String updatedBy;
	private Date updatedOn;
	
	private String role;

	public NamePronunciation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getLegalFName() {
		return legalFName;
	}

	public void setLegalFName(String legalFName) {
		this.legalFName = legalFName;
	}

	public String getLegalLName() {
		return legalLName;
	}

	public void setLegalLName(String legalLName) {
		this.legalLName = legalLName;
	}

	public String getPrefName() {
		return prefName;
	}

	public void setPrefName(String prefName) {
		this.prefName = prefName;
	}

	public byte[] getPrefPronunciation() {
		return prefPronunciation;
	}

	public void setPrefPronunciation(byte[] prefPronunciation) {
		this.prefPronunciation = prefPronunciation;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
