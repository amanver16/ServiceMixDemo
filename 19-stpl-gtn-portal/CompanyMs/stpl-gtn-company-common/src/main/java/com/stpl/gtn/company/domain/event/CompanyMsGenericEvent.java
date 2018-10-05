package com.stpl.gtn.company.domain.event;

public class CompanyMsGenericEvent {

	public CompanyMsGenericEvent() {
		super();
	}

	private String eventName;

	private String aggregateId;

	private String aggregateType;

	private String raisedTime;

	private String originCommandRequestId;

	private String originCommandName;

	private String originAggregateId;

	private String originIssuedTime;

	private String eventData;

	private String version = "1";

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getAggregateType() {
		return aggregateType;
	}

	public void setAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getRaisedTime() {
		return raisedTime;
	}

	public void setRaisedTime(String raisedTime) {
		this.raisedTime = raisedTime;
	}

	public String getOriginCommandRequestId() {
		return originCommandRequestId;
	}

	public void setOriginCommandRequestId(String originCommandRequestId) {
		this.originCommandRequestId = originCommandRequestId;
	}

	public String getOriginCommandName() {
		return originCommandName;
	}

	public void setOriginCommandName(String originCommandName) {
		this.originCommandName = originCommandName;
	}

	public String getOriginAggregateId() {
		return originAggregateId;
	}

	public void setOriginAggregateId(String originAggregateId) {
		this.originAggregateId = originAggregateId;
	}

	public String getOriginIssuedTime() {
		return originIssuedTime;
	}

	public void setOriginIssuedTime(String originIssuedTime) {
		this.originIssuedTime = originIssuedTime;
	}

	public String getEventData() {
		return eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
