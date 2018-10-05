package com.stpl.hr.asset.domain.event;

public class AssetMsAssetAddingFailed {

	public AssetMsAssetAddingFailed() {
		super();
	}

	private String assetId;
	private String failureReason;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

}
