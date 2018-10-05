package com.stpl.gtn.item.domain.command;

public enum ItemMsCommandType {
	AddItem("AddItem"), UpdateItem("UpdateItem"), DeleteItem("DeleteItem");

	String commandName;

	private ItemMsCommandType(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
