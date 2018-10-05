package com.stpl.hr.asset.domain.command;

public class AssetMSAllocateAsset {

	public AssetMSAllocateAsset() {
		super();
	}

	private String assetOwnerId;
	private String assetOwner;

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

}
