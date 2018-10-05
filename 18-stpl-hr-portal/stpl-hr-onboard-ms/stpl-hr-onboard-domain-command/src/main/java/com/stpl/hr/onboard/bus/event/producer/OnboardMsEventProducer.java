package com.stpl.hr.onboard.bus.event.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.onboard.common.service.OnboardMsDateService;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsEventProducer {

	@Produce(uri = "jms:topic:loginMsEventBus")
	ProducerTemplate onboardMsEventProducer;

	public void raiseEvent(OnboardMsGenericEvent event) {
		try {

			event.setRaisedTime(OnboardMsDateService.getCurrentDateInString());
			String eventJson = OnboardMSJsonService.convertObjectToJson(event);
			System.out.println("Onboard MS : Sending event with " + event.getEventName() + " requestId "
					+ event.getOriginCommandRequestId() + " eventJson " + eventJson);
			onboardMsEventProducer.sendBody(eventJson);

		} catch (CamelExecutionException e) {
			System.out.println("Onboard MS :Error in sending event to the queue " + event.getEventName());
			e.printStackTrace();
		}
	}

}
