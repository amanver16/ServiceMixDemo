package com.stpl.hr.asset.mvc.request;

public class AssetMSAssetInventoryRequest {
	
	public AssetMSAssetInventoryRequest() {
	super();
	}
	private String assetId;
	private String assetType;
	private String specification;

	
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
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}


}
