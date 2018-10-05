package com.stpl.hr.onboard.domain.event;

public enum OnboardMsEventType {

	OnBoardInitiated("onboardInitiated"), 
	
	OnBoardInitiationFailed("OnBoardInitiationFailed"), 
	
	NewUserRegistered("newUserRegistered"),
	
	AssetAllocated("AssetAllocated");

	private String eventName;

	private OnboardMsEventType(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

}
