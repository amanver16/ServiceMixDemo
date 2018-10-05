package com.stpl.hr.asset.domain.aggregate.asset.type;

public enum AssetMsAssetType {

	DESKTOP("DESKTOP"), LAPTOP("LAPTOP"), IPPHONE("IPPHONE");

	private AssetMsAssetType(String type) {
		this.type = type;
	}

	private String type;

	public String getType() {
		return type;
	}

}
