package com.stpl.gtn.item.domain.eventstore.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.gtn.item.domain.event.ItemMsEventType;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.eventstore.mongo.ItemMsMongoDBConnectionService;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsEventStoreHandler {
	private Map<String, ItemMsEventType> supportedEventMap;
	private ItemMsMongoDBConnectionService eventStoreDbSevice;

	public ItemMsEventStoreHandler() {
		super();
		supportedEventMap = new HashMap<String, ItemMsEventType>();
		supportedEventMap.put(ItemMsEventType.ItemAdded.getEventName(), ItemMsEventType.ItemAdded);
		supportedEventMap.put(ItemMsEventType.ItemAdditionFailed.getEventName(), ItemMsEventType.ItemAdditionFailed);
		supportedEventMap.put(ItemMsEventType.ItemUpdated.getEventName(), ItemMsEventType.ItemUpdated);
		supportedEventMap.put(ItemMsEventType.ItemUpdationFailed.getEventName(), ItemMsEventType.ItemUpdationFailed);
		supportedEventMap.put(ItemMsEventType.ItemDeleted.getEventName(), ItemMsEventType.ItemDeleted);
		supportedEventMap.put(ItemMsEventType.ItemDeletionFailed.getEventName(), ItemMsEventType.ItemDeletionFailed);
	}

	public void setEventStoreDbSevice(ItemMsMongoDBConnectionService eventStoreDbSevice) {
		this.eventStoreDbSevice = eventStoreDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Item MS: Event Store-Write Model : Receiving event " + content);
		ItemMsGenericEvent event = ItemMsJsonService.buildEventFromJson(content);
		try {
			if (supportedEventMap.get(event.getEventName()) == null) {
				System.out.println("ItemMS: Event Store-Write Model : IgnoringEvent: " + event.getEventName());
				return;
			}
			int duplicateCount = eventStoreDbSevice.checkForDuplicateAggregate(event.getAggregateId());
			if (duplicateCount == 0) {
				eventStoreDbSevice.addAggregate(event);
			}
			eventStoreDbSevice.addAggregateEvent(event, content);
			System.out.println("Item MS: Event Store-Write Model : event stored " + content);

		} catch (Exception e) {
			System.out.println("Item MS: Event Store-Write Model : Error in storing event");
			e.printStackTrace();
		}
	}

}
