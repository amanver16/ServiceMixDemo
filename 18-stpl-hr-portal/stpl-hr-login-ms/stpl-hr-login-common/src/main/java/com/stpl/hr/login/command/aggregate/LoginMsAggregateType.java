package com.stpl.hr.login.command.aggregate;

public enum LoginMsAggregateType {

	UserAggregate("User");

	String aggregateType;

	private LoginMsAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getAggregateType() {
		return aggregateType;
	}

}
