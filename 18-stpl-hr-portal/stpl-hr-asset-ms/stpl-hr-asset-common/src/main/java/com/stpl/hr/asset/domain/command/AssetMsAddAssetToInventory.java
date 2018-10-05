package com.stpl.hr.asset.domain.command;

public class AssetMsAddAssetToInventory {

	public AssetMsAddAssetToInventory() {
		super();
	}

	private String assetId;
	private String assetType;
	private String assetSpecification;

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

	public String getAssetSpecification() {
		return assetSpecification;
	}

	public void setAssetSpecification(String assetSpecification) {
		this.assetSpecification = assetSpecification;
	}


}
