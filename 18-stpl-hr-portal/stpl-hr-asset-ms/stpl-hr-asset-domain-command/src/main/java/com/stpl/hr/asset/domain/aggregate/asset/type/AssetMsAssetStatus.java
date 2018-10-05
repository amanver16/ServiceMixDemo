package com.stpl.hr.asset.domain.aggregate.asset.type;

public enum AssetMsAssetStatus {

	VACANT("VACANT"), OCCUPIED("OCCUPIED"), ONHOLD("ONHOLD"), DEACTIVATED("DEACTIVATED"), NOTADDED("NOTADDED");

	private AssetMsAssetStatus(String status) {
		this.status = status;
	}

	private String status;

	public String getStatus() {
		return status;
	}

}
