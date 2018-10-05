package com.stpl.gtn.company.domain.command;

public enum CompanyMsCommandType {
	AddCompany("AddCompany"), UpdateCompany("UpdateCompany"), DeleteCompany("DeleteCompany");

	String commandName;

	private CompanyMsCommandType(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
