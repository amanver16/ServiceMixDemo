package com.stpl.gtn.item.domain.query.handler;

import java.io.IOException;

import com.stpl.gtn.item.domain.event.ItemMsEventType;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.ItemMsItemAdded;
import com.stpl.gtn.item.domain.query.mongo.service.ItemMsQueryMongoService;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsDomainQueryHandler {
	private ItemMsQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(ItemMsQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Item MS Domain Query Model : Receiving event " + content);
		ItemMsGenericEvent event = ItemMsJsonService.buildEventFromJson(content);
		if (ItemMsEventType.ItemAdded.getEventName().equals(event.getEventName())) {
			handleItemAddedEvent(event);
			return;
		}

		if (ItemMsEventType.ItemUpdated.getEventName().equals(event.getEventName())) {
			handleItemUpdatedEvent(event);
			return;
		}

		if (ItemMsEventType.ItemDeleted.getEventName().equals(event.getEventName())) {
			handleItemDeletedEvent(event);
			return;
		}

		System.out.println("Item MS Domain Query Model: Ignoring event " + event.getEventName());
	}

	public void handleItemAddedEvent(ItemMsGenericEvent event) throws IOException {

		ItemMsItemAdded item = (ItemMsItemAdded) ItemMsJsonService.convertJsonToObject(ItemMsItemAdded.class,
				event.getEventData());

		item.getItemMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.insertItem(item.getItemMasterBean());
	}

	public void handleItemUpdatedEvent(ItemMsGenericEvent event) throws IOException {

		ItemMsItemAdded item = (ItemMsItemAdded) ItemMsJsonService.convertJsonToObject(ItemMsItemAdded.class,
				event.getEventData());

		item.getItemMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.updateItem(item.getItemMasterBean());
	}

	public void handleItemDeletedEvent(ItemMsGenericEvent event) throws IOException {

		ItemMsItemAdded item = (ItemMsItemAdded) ItemMsJsonService.convertJsonToObject(ItemMsItemAdded.class,
				event.getEventData());

		item.getItemMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.deleteItem(item.getItemMasterBean());
	}
}
