package com.stpl.hr.onboard.domain.aggregate;

public enum OnboardMsAggregateType {

	AssociateAggregate("Associate");

	String aggregateType;

	private OnboardMsAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getAggregateType() {
		return aggregateType;
	}

}
