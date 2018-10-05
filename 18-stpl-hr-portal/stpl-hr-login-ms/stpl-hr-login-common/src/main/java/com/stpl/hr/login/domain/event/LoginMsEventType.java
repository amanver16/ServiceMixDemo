package com.stpl.hr.login.domain.event;

public enum LoginMsEventType {

	NewUserRegistered("newUserRegistered"),

	UserLoggedIn("userLoggedIn"),

	UserLoggedInFailed("userLoggedInFailed"),

	PasswordUpdated("passwordUpdated"),

	PasswordUpdateFailed("passwordUpdateFailed"),

	OnBoardInitiated("onboardInitiated");

	String eventName;

	private LoginMsEventType(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

}
