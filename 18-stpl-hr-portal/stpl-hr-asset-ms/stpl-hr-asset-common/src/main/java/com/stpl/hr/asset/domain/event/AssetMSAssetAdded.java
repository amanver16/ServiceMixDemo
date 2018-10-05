package com.stpl.hr.asset.domain.event;

public class AssetMSAssetAdded {
	public AssetMSAssetAdded() {
		super();
	}

	private String assetId;
	private String assetType;
	private String assetSpecification;
	private String assetStatus;

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

	public String getAssetStatus() {
		return assetStatus;
	}

	public void setAssetStatus(String assetStatus) {
		this.assetStatus = assetStatus;
	}

	public String getAssetSpecification() {
		return assetSpecification;
	}

	public void setAssetSpecification(String assetSpecification) {
		this.assetSpecification = assetSpecification;
	}

}
