package com.stpl.hr.asset.domain.query.data.bean;

public class AssetMsAssetInventoryBean {

	public AssetMsAssetInventoryBean() {
		super();
	}

	private String assetAggregateId;
	private String assetId;
	private String assetType;
	private String assetSepcification;
	private String assetStatus;

	private String assetOwner;
	private String assetOwnerId;
	private String assetStartDate;
	private String assetEndDate;

	public String getAssetAggregateId() {
		return assetAggregateId;
	}

	public void setAssetAggregateId(String assetAggregateId) {
		this.assetAggregateId = assetAggregateId;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetSepcification() {
		return assetSepcification;
	}

	public void setAssetSepcification(String assetSepcification) {
		this.assetSepcification = assetSepcification;
	}

	public String getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getAssetOwner() {
		return assetOwner;
	}

	public void setAssetOwner(String assetOwner) {
		this.assetOwner = assetOwner;
	}
	
	public String getAssetOwnerId() {
		return assetOwnerId;
	}

	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

	public String getAssetStartDate() {
		return assetStartDate;
	}

	public void setAssetStartDate(String assetStartDate) {
		this.assetStartDate = assetStartDate;
	}

	public String getAssetEndDate() {
		return assetEndDate;
	}

	public void setAssetEndDate(String assetEndDate) {
		this.assetEndDate = assetEndDate;
	}

}
