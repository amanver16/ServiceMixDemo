package com.stpl.hr.asset.bus.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsEventTracker {
	public AssetMsEventTracker() {
		super();
	}

	private Map<String, AssetMsGenericEvent> eventResponseMap = new HashMap<>();
	private Map<String, String> requestIdMap = new HashMap<>();

	public void putEvent(String message) throws IOException {
		System.out.println("Asset MS MVC Layer : AssetMsEventTracker Receving event " + message);
		AssetMsGenericEvent eventResponse = AssetMSJsonService.buildEventFromJson(message);
		if (requestIdMap.get(eventResponse.getOriginCommandRequestId()) == null) {
			System.out.println("Asset MS MVC Layer : AssetMsEventTracker Ignoring event " + message);
			return;
		}
		eventResponseMap.put(eventResponse.getOriginCommandRequestId(), eventResponse);
	}

	public AssetMsGenericEvent pollEvent(String originCommandRequestId, int timeOutSeconds) {
		for (int i = 1; i < timeOutSeconds + 1; i++) {
			try {

				Thread.sleep(1000);
				AssetMsGenericEvent eventResponse = eventResponseMap.get(originCommandRequestId);
				if (eventResponse != null) {
					removeRequestFromMap(originCommandRequestId);
					return eventResponse;
				}
				System.out.println(
						"Asset MS Waiting for " + i + " seconds for response (requestId) " + originCommandRequestId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Asset MS Event not received");
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
