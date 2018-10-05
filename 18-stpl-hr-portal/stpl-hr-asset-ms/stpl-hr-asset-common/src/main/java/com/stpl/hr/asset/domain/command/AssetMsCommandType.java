package com.stpl.hr.asset.domain.command;

public enum AssetMsCommandType {

	allocateAsset("allocateAsset"), addAssetToInventory("addAssetToInventory");

	String commandName;

	private AssetMsCommandType(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
