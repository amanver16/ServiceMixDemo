package com.stpl.hr.onboard.domain.command;

public enum OnboardMsCommandType {

	InitiateOnboarding("initiateOnboarding");

	String commandName;

	private OnboardMsCommandType(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
