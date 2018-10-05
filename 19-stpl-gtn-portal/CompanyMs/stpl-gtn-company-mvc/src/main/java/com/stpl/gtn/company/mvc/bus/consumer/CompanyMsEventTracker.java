package com.stpl.gtn.company.mvc.bus.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsEventTracker {
	private Map<String, CompanyMsGenericEvent> eventResponseMap = new HashMap<>();
	private Map<String, String> requestIdMap = new HashMap<>();

	public void putEvent(String message) throws IOException {
		System.out.println("Company MS MVC Layer : CompanyMsEventTracker Receving event " + message);
		CompanyMsGenericEvent eventResponse = CompanyMsJsonService.buildEventFromJson(message);
		if (requestIdMap.get(eventResponse.getOriginCommandRequestId()) == null) {
			System.out.println("Company MS MVC Layer : CompanyMsEventTracker Ignoring event " + message);
			return;
		}
		eventResponseMap.put(eventResponse.getOriginCommandRequestId(), eventResponse);
	}

	public CompanyMsGenericEvent pollEvent(String originCommandRequestId, int timeOutSeconds) {
		for (int i = 1; i < timeOutSeconds + 1; i++) {
			try {

				Thread.sleep(1000);
				CompanyMsGenericEvent eventResponse = eventResponseMap.get(originCommandRequestId);
				if (eventResponse != null) {
					removeRequestFromMap(originCommandRequestId);
					return eventResponse;
				}
				System.out.println(
						"Company MS Waiting for " + i + " seconds for response (requestId) " + originCommandRequestId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Company MS Event not received");
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
