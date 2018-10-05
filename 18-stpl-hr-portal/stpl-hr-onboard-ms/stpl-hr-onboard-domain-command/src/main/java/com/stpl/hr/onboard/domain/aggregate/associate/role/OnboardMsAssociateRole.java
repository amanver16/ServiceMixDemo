package com.stpl.hr.onboard.domain.aggregate.associate.role;

public enum OnboardMsAssociateRole {

	DEV("DEV"), QA("QA"), HR("HR"), TL("TL"), PM("PM"), RM("RM");

	private OnboardMsAssociateRole(String role) {
		this.role = role;
	}

	private String role;

	public String getRole() {
		return role;
	}

}
