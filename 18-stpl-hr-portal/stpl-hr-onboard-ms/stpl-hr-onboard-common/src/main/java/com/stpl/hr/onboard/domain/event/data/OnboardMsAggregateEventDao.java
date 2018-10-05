package com.stpl.hr.onboard.domain.event.data;

public class OnboardMsAggregateEventDao {

	public OnboardMsAggregateEventDao() {
		super();
	}

	private String eventName;
	private String eventData;
	private String version;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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
