package com.stpl.gtn.item.domain.aggregate;

public enum ItemMsAggregateType {

	ItemAggregate("Item");

	String aggregateType;

	private ItemMsAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	public String getAggregateType() {
		return aggregateType;
	}

}
