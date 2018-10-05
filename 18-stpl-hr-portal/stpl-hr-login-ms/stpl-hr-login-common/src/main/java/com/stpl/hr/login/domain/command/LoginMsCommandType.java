package com.stpl.hr.login.domain.command;

public enum LoginMsCommandType {

	CheckUserCredentials("checkUserCredentials"), RegisterNewUser("registerNewUser"), UpdatePassword("updatePassword");

	String commandName;

	private LoginMsCommandType(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
