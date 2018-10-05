package com.stpl.hr.login.bus.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMsEventTracker {

	private Map<String, LoginMsGenericEvent> eventResponseMap = new HashMap<>();
	private Map<String, String> requestIdMap = new HashMap<>();

	public void putEvent(String message) throws IOException {
		System.out.println("LoginMs : MVC Layer : LoginMsEventTracker Receving event " + message);
		LoginMsGenericEvent eventResponse = LoginMSJsonService.buildEventFromJson(message);
		if (requestIdMap.get(eventResponse.getOriginCommandRequestId()) == null) {
			System.out.println("LoginMs : MVC Layer : LoginMsEventTracker Ignoring event " + message);
			return;
		}
		eventResponseMap.put(eventResponse.getOriginCommandRequestId(), eventResponse);
	}

	public LoginMsGenericEvent pollEvent(String originCommandRequestId, int timeOutSeconds) {
		for (int i = 1; i < timeOutSeconds + 1; i++) {
			try {

				Thread.sleep(1000);
				LoginMsGenericEvent eventResponse = eventResponseMap.get(originCommandRequestId);
				if (eventResponse != null) {
					removeRequestFromMap(originCommandRequestId);
					return eventResponse;
				}
				System.out.println(
						"LoginMs : Waiting for " + i + " seconds for response (requestId) " + originCommandRequestId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("LoginMs : Event not received");
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
