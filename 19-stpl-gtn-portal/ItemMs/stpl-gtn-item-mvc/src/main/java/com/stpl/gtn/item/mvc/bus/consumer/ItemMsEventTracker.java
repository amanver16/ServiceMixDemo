package com.stpl.gtn.item.mvc.bus.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsEventTracker {
	private Map<String, ItemMsGenericEvent> eventResponseMap = new HashMap<>();
	private Map<String, String> requestIdMap = new HashMap<>();

	public void putEvent(String message) throws IOException {
		System.out.println("Item MS MVC Layer : ItemMsEventTracker Receving event " + message);
		ItemMsGenericEvent eventResponse = ItemMsJsonService.buildEventFromJson(message);
		if (requestIdMap.get(eventResponse.getOriginCommandRequestId()) == null) {
			System.out.println("Item MS MVC Layer : ItemMsEventTracker Ignoring event " + message);
			return;
		}
		eventResponseMap.put(eventResponse.getOriginCommandRequestId(), eventResponse);
	}

	public ItemMsGenericEvent pollEvent(String originCommandRequestId, int timeOutSeconds) {
		for (int i = 1; i < timeOutSeconds + 1; i++) {
			try {

				Thread.sleep(1000);
				ItemMsGenericEvent eventResponse = eventResponseMap.get(originCommandRequestId);
				if (eventResponse != null) {
					removeRequestFromMap(originCommandRequestId);
					return eventResponse;
				}
				System.out.println(
						"Item MS Waiting for " + i + " seconds for response (requestId) " + originCommandRequestId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Item MS Event not received");
		removeRequestFromMap(originCommandRequestId);
		return null;
	}

	private void removeRequestFromMap(String originCommandRequestId) {
		eventResponseMap.remove(originCommandRequestId);
		requestIdMap.remove(originCommandRequestId);
	}

	public void addRequestIdInMap(String commandRequestId) {
		requestIdMap.put(commandRequestId, "test");
	}

}
