package com.stpl.hr.asset.domain.aggregate;

public enum AssetMsAggregateType {

	AssetAggregate("Asset");

	String aggregateType;

	private AssetMsAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getAggregateType() {
		return aggregateType;
	}

}
