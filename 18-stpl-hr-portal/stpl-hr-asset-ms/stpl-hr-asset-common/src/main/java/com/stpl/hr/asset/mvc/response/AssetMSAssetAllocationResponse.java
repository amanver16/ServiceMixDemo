package com.stpl.hr.asset.mvc.response;

public class AssetMSAssetAllocationResponse {
	
	public AssetMSAssetAllocationResponse() {
	super();
	}
	private String assetId;
	private String assetType;
	private String allocatedTo;
	
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
	public String getAllocatedTo() {
		return allocatedTo;
	}
	public void setAllocatedTo(String allocatedTo) {
		this.allocatedTo = allocatedTo;
	}
	
	

}
