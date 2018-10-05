package com.stpl.hr.asset.domain.event.store.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.event.store.mongo.service.AssetMSEventStoreMongoService;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsEventStoreHandler {
	private Map<String, AssetMsEventType> supportedEventMap;

	public AssetMsEventStoreHandler() {
		super();
		supportedEventMap = new HashMap<String, AssetMsEventType>();
		supportedEventMap.put(AssetMsEventType.AssetAdded.getEventName(), AssetMsEventType.AssetAdded);
		supportedEventMap.put(AssetMsEventType.AssetAddingFailed.getEventName(), AssetMsEventType.AssetAddingFailed);
		supportedEventMap.put(AssetMsEventType.AssetAllocated.getEventName(), AssetMsEventType.AssetAllocated);
		supportedEventMap.put(AssetMsEventType.AssetAllocationFailed.getEventName(),
				AssetMsEventType.AssetAllocationFailed);
	}

	private AssetMSEventStoreMongoService eventStoreDbSevice;

	public void setEventStoreDbSevice(AssetMSEventStoreMongoService eventStoreDbSevice) {
		this.eventStoreDbSevice = eventStoreDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Asset MS: Event Store-Write Model : Receiving event " + content);
		AssetMsGenericEvent event = AssetMSJsonService.buildEventFromJson(content);
		try {
			if (supportedEventMap.get(event.getEventName()) == null) {
				System.out.println("AssetMS: Event Store-Write Model : IgnoringEvent: " + event.getEventName());
				return;
			}
			int duplicateCount = eventStoreDbSevice.checkForDuplicateAggregate(event.getAggregateId());
			if (duplicateCount == 0) {
				eventStoreDbSevice.addAggregate(event);
			}
			eventStoreDbSevice.addAggregateEvent(event, content);
			System.out.println("Asset MS: Event Store-Write Model : event stored " + content);

		} catch (SQLException e) {
			System.out.println("Asset MS: Event Store-Write Model : Error in storing event ");
			e.printStackTrace();
		}
	}

}
