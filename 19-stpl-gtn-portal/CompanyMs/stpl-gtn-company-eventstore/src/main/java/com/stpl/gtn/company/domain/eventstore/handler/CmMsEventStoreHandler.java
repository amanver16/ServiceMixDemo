package com.stpl.gtn.company.domain.eventstore.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.gtn.company.domain.event.CompanyMsEventType;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.eventstore.mongo.CmMsMongoDBConnectionService;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CmMsEventStoreHandler {
	private Map<String, CompanyMsEventType> supportedEventMap;
	private CmMsMongoDBConnectionService eventStoreDbSevice;

	public CmMsEventStoreHandler() {
		super();
		supportedEventMap = new HashMap<String, CompanyMsEventType>();
		supportedEventMap.put(CompanyMsEventType.CompanyAdded.getEventName(), CompanyMsEventType.CompanyAdded);
		supportedEventMap.put(CompanyMsEventType.CompanyAdditionFailed.getEventName(),
				CompanyMsEventType.CompanyAdditionFailed);
		supportedEventMap.put(CompanyMsEventType.CompanyUpdated.getEventName(), CompanyMsEventType.CompanyUpdated);
		supportedEventMap.put(CompanyMsEventType.CompanyUpdationFailed.getEventName(),
				CompanyMsEventType.CompanyUpdationFailed);
		supportedEventMap.put(CompanyMsEventType.CompanyDeleted.getEventName(), CompanyMsEventType.CompanyDeleted);
		supportedEventMap.put(CompanyMsEventType.CompanyDeletionFailed.getEventName(),
				CompanyMsEventType.CompanyDeletionFailed);
	}

	public void setEventStoreDbSevice(CmMsMongoDBConnectionService eventStoreDbSevice) {
		this.eventStoreDbSevice = eventStoreDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Cm MS: Event Store-Write Model : Receiving event " + content);
		CompanyMsGenericEvent event = CompanyMsJsonService.buildEventFromJson(content);
		try {
			if (supportedEventMap.get(event.getEventName()) == null) {
				System.out.println("CmMS: Event Store-Write Model : IgnoringEvent: " + event.getEventName());
				return;
			}
			int duplicateCount = eventStoreDbSevice.checkForDuplicateAggregate(event.getAggregateId());
			if (duplicateCount == 0) {
				eventStoreDbSevice.addAggregate(event);
			}
			eventStoreDbSevice.addAggregateEvent(event, content);
			System.out.println("CM MS: Event Store-Write Model : event stored " + content);

		} catch (Exception e) {
			System.out.println("CM MS: Event Store-Write Model : Error in storing event");
			e.printStackTrace();
		}
	}

}
