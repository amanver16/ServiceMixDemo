package com.stpl.hr.asset.mvc.request;


public class AssetMSAssetAllocationRequest {
	
	public AssetMSAssetAllocationRequest() {
		super();
		
	}
	private String assetId;
	private String status;
	private String allocatedTo;
	
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
	public String getAllocatedTo() {
		return allocatedTo;
	}
	public void setAllocatedTo(String allocatedTo) {
		this.allocatedTo = allocatedTo;
	}

}
