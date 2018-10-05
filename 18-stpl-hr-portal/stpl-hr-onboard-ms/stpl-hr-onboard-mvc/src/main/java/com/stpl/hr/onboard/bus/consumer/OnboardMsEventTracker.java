package com.stpl.hr.onboard.bus.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsEventTracker {
	private Map<String, OnboardMsGenericEvent> eventResponseMap = new HashMap<>();
	private Map<String, String> requestIdMap = new HashMap<>();

	public void putEvent(String message) throws IOException {
		System.out.println("Onboard MS MVC Layer : OnboardMsEventTracker Receving event " + message);
		OnboardMsGenericEvent eventResponse = OnboardMSJsonService.buildEventFromJson(message);
		if (requestIdMap.get(eventResponse.getOriginCommandRequestId()) == null) {
			System.out.println("Onboard MS MVC Layer : OnboardMsEventTracker Ignoring event " + message);
			return;
		}
		eventResponseMap.put(eventResponse.getOriginCommandRequestId(), eventResponse);
	}

	public OnboardMsGenericEvent pollEvent(String originCommandRequestId, int timeOutSeconds) {
		for (int i = 1; i < timeOutSeconds + 1; i++) {
			try {

				Thread.sleep(1000);
				OnboardMsGenericEvent eventResponse = eventResponseMap.get(originCommandRequestId);
				if (eventResponse != null) {
					removeRequestFromMap(originCommandRequestId);
					return eventResponse;
				}
				System.out.println(
						"Onboard MS Waiting for " + i + " seconds for response (requestId) " + originCommandRequestId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Onboard MS Event not received");
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
