package com.stpl.hr.onboard.domain.event.store.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.onboard.domain.event.OnboardMsEventType;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.store.mongo.service.OnboardMSEventStoreMongoService;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsEventStoreHandler {
	private Map<String, OnboardMsEventType> supportedEventMap;

	public OnboardMsEventStoreHandler() {
		super();
		supportedEventMap = new HashMap<>();
		supportedEventMap.put(OnboardMsEventType.OnBoardInitiated.getEventName(), OnboardMsEventType.OnBoardInitiated);
		supportedEventMap.put(OnboardMsEventType.OnBoardInitiationFailed.getEventName(),
				OnboardMsEventType.OnBoardInitiationFailed);
	}

	private OnboardMSEventStoreMongoService eventStoreDbSevice;

	public void setEventStoreDbSevice(OnboardMSEventStoreMongoService eventStoreDbSevice) {
		this.eventStoreDbSevice = eventStoreDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Onboard MS: Event Store-Write Model : Receiving event " + content);
		OnboardMsGenericEvent event = OnboardMSJsonService.buildEventFromJson(content);
		try {
			if (supportedEventMap.get(event.getEventName()) == null) {
				System.out.println("OnboardMS: Event Store-Write Model : Ignoring Event: " + event.getEventName());
				return;
			}
			int duplicateCount = eventStoreDbSevice.checkForDuplicateAggregate(event.getAggregateId());
			if (duplicateCount == 0) {
				eventStoreDbSevice.addAggregate(event);
			}
			eventStoreDbSevice.addAggregateEvent(event, content);
			System.out.println("Onboard MS: Event Store-Write Model : event stored " + content);

		} catch (SQLException e) {
			System.out.println("Onboard MS: Event Store-Write Model : Error in storing event ");
			e.printStackTrace();
		}
	}

}
