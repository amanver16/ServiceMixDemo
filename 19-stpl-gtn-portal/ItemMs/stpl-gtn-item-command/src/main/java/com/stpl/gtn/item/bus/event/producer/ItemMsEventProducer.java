package com.stpl.gtn.item.bus.event.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.item.common.service.ItemMsDateService;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsEventProducer {
	@Produce(uri = "jms:topic:itemMsEventBus")
	ProducerTemplate ItemMsEventProducer;

	public void raiseEvent(ItemMsGenericEvent event) {
		try {

			event.setRaisedTime(ItemMsDateService.getCurrentDateInString());
			String eventJson = ItemMsJsonService.convertObjectToJson(event);
			System.out.println("Item MS : Sending event with " + event.getEventName() + " requestId "
					+ event.getOriginCommandRequestId() + " eventJson " + eventJson);
			ItemMsEventProducer.sendBody(eventJson);

		} catch (CamelExecutionException e) {
			System.out.println("Item MS :Error in sending event to the queue " + event.getEventName());
			e.printStackTrace();
		}
	}
}
