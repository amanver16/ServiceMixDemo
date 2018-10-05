package com.stpl.hr.asset.bus.event.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.asset.common.service.AssetMsDateService;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsEventProducer {

	@Produce(uri = "jms:topic:loginMsEventBus")
	ProducerTemplate assetMsEventProducer;

	public void raiseEvent(AssetMsGenericEvent event) {
		try {

			event.setRaisedTime(AssetMsDateService.getCurrentDateInString());
			String eventJson = AssetMSJsonService.convertObjectToJson(event);
			System.out.println("Asset MS : Sending event with " + event.getEventName() + " requestId "
					+ event.getOriginCommandRequestId() + " eventJson " + eventJson);
			assetMsEventProducer.sendBody(eventJson);

		} catch (CamelExecutionException e) {
			System.out.println("Asset MS :Error in sending event to the queue " + event.getEventName());
			e.printStackTrace();
		}
	}

}
