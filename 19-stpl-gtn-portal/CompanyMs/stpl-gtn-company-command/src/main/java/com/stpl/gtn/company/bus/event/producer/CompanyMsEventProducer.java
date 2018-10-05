package com.stpl.gtn.company.bus.event.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.company.common.service.CompanyMsDateService;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsEventProducer {
	@Produce(uri = "jms:topic:companyMsEventBus")
	ProducerTemplate companyMsEventProducer;

	public void raiseEvent(CompanyMsGenericEvent event) {
		try {

			event.setRaisedTime(CompanyMsDateService.getCurrentDateInString());
			String eventJson = CompanyMsJsonService.convertObjectToJson(event);
			System.out.println("Company MS : Sending event with " + event.getEventName() + " requestId "
					+ event.getOriginCommandRequestId() + " eventJson " + eventJson);
			companyMsEventProducer.sendBody(eventJson);

		} catch (CamelExecutionException e) {
			System.out.println("Company MS :Error in sending event to the queue " + event.getEventName());
			e.printStackTrace();
		}
	}
}
