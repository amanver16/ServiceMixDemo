package com.stpl.gtn.item.domain.event;

public enum ItemMsEventType {
	ItemAdded("ItemAdded"), ItemAdditionFailed("ItemAdditionFailed"), ItemUpdated("ItemUpdated"), ItemUpdationFailed(
			"ItemUpdationFailed"), ItemDeletionFailed("ItemDeletionFailed"), ItemDeleted("ItemDeleted");
	private String eventName;

	private ItemMsEventType(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

}
