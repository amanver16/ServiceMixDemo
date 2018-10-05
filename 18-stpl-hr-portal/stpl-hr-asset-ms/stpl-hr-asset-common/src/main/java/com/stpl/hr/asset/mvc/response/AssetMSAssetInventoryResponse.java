package com.stpl.hr.asset.mvc.response;

public class AssetMSAssetInventoryResponse {
	
	public AssetMSAssetInventoryResponse() {
		super();
	}
	
	private String assetId;
	private String status;
	private String failureReason;
	
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	

}
