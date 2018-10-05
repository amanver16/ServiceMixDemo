package com.stpl.hr.asset.domain.command;

public class AssetMsGenericCommand {

	private String aggregateId;
	private String commandName;
	private String issuedTime;
	private String commandData;
	private String originCommandRequestId;
	private String originEventAggregateId;
	private String originEventName;
	private String originEventRaisedTime;

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getIssuedTime() {
		return issuedTime;
	}

	public void setIssuedTime(String issuedTime) {
		this.issuedTime = issuedTime;
	}

	public String getOriginEventAggregateId() {
		return originEventAggregateId;
	}

	public void setOriginEventAggregateId(String originEventAggregateId) {
		this.originEventAggregateId = originEventAggregateId;
	}

	public String getOriginEventName() {
		return originEventName;
	}

	public void setOriginEventName(String originEventName) {
		this.originEventName = originEventName;
	}

	public String getCommandData() {
		return commandData;
	}

	public void setCommandData(String commandData) {
		this.commandData = commandData;
	}

	public String getOriginCommandRequestId() {
		return originCommandRequestId;
	}

	public void setOriginCommandRequestId(String originCommandRequestId) {
		this.originCommandRequestId = originCommandRequestId;
	}

	public String getOriginEventRaisedTime() {
		return originEventRaisedTime;
	}

	public void setOriginEventRaisedTime(String originEventRaisedTime) {
		this.originEventRaisedTime = originEventRaisedTime;
	}

}
