package com.stpl.hr.asset.test;

import com.stpl.hr.asset.domain.event.AssetMSAssetAdded;
import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsSampleEventCreator {

	public static void main(String args[]) {

		AssetMsGenericEvent genericResponse = new AssetMsGenericEvent();
		AssetMSAssetAdded assetAddedEventData = new AssetMSAssetAdded();
		genericResponse.setAggregateId("need to change");
		genericResponse.setEventName(AssetMsEventType.AssetAdded.getEventName());
		genericResponse.setOriginCommandRequestId("d7dba4a4-57ea-4999-8536-3d00eba1990f");
		assetAddedEventData.setAssetId("WS-11");
		genericResponse.setEventData(AssetMSJsonService.convertObjectToJson(assetAddedEventData));
		System.out.println(AssetMSJsonService.convertObjectToJson(genericResponse));
	}

}
