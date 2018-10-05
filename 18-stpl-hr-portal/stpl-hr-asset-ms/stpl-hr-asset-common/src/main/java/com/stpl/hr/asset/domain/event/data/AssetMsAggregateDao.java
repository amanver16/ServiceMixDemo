package com.stpl.hr.asset.domain.event.data;

import java.util.ArrayList;
import java.util.List;

public class AssetMsAggregateDao {

	public AssetMsAggregateDao() {
		super();
	}

	private String aggregateId;
	private String aggregateType;
	private List<AssetMsAggregateEventDao> aggregateEventList;
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

	public List<AssetMsAggregateEventDao> getAggregateEventList() {
		return aggregateEventList;
	}

	public void addAggregateEvent(AssetMsAggregateEventDao aggregateEvent) {
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
