package com.stpl.hr.asset.domain.event;

public class AssetMsAssetAllocated {
	public AssetMsAssetAllocated() {
		super();
	}

	private String assetId;
	private String assetStatus;
	private String assetOwner;
	private String assetOwnerId;
	private String assetStartDate;
	private String assetEndDate;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
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

	public String getAssetOwnerId() {
		return assetOwnerId;
	}

	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

}
