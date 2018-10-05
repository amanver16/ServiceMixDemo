package com.stpl.hr.asset.domain.event;

public class AssetMsAssetAllocationFailed {

	public AssetMsAssetAllocationFailed() {
		super();
	}

	private String assetId;
	private String assetOwner;
	private String assetOwnerId;

	private String assetAggregateId;
	private String failureReason;

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetAggregateId() {
		return assetAggregateId;
	}

	public void setAssetAggregateId(String assetAggregateId) {
		this.assetAggregateId = assetAggregateId;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
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

}
