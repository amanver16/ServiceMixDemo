package com.stpl.hr.login.domain.event.data;

import java.util.ArrayList;
import java.util.List;

public class LoginMsAggregate {
	public LoginMsAggregate() {
		super();
	}

	private String aggregateId;
	private String aggregateType;
	private List<LoginMsAggregateEvent> aggregateEventList;
	private boolean aggregateNotFound = false;
	private int eventCount = 0;

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

	public List<LoginMsAggregateEvent> getAggregateEventList() {
		return aggregateEventList;
	}

	public void addAggregateEvent(LoginMsAggregateEvent aggregateEvent) {
		if (aggregateEventList == null) {
			aggregateEventList = new ArrayList<>();
		}
		this.aggregateEventList.add(aggregateEvent);
		eventCount++;
	}

	public boolean isAggregateNotFound() {
		return aggregateNotFound;
	}

	public void setAggregateNotFound(boolean aggregateNotFound) {
		this.aggregateNotFound = aggregateNotFound;
	}

	public int getEventCount() {
		return eventCount;
	}

}
