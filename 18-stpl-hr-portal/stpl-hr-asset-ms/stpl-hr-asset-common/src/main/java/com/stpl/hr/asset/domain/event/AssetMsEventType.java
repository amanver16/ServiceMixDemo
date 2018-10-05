package com.stpl.hr.asset.domain.event;

public enum AssetMsEventType {

	AssetAllocated("AssetAllocated"), AssetAdded("AssetAdded"), AssetAllocationFailed(
			"AssetAllocationFailed"), AssetAddingFailed("AssetAddingFailed"), OnBoardInitiated("onboardInitiated");

	private String eventName;

	private AssetMsEventType(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

}
