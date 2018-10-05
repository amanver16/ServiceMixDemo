package com.stpl.gtn.company.domain.aggregate;

public enum CompanyMsAggregateType {

	CompanyAggregate("Company");

	String aggregateType;

	private CompanyMsAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getAggregateType() {
		return aggregateType;
	}

}
