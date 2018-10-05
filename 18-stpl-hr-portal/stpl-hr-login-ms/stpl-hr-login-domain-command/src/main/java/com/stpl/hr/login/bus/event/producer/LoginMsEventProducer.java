package com.stpl.hr.login.bus.event.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.login.common.service.LoginMsDateService;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

@Component
public class LoginMsEventProducer {

	@Produce(uri = "jms:topic:loginMsEventBus")
	ProducerTemplate loginMsEventProducer;

	public void raiseEvent(LoginMsGenericEvent event) {
		try {

			event.setRaisedTime(LoginMsDateService.getCurrentDateInString());
			String eventJson = LoginMSJsonService.convertObjectToJson(event);
			System.out.println("LoginMs : Command : Sending event with " + event.getEventName() + " requestId "
					+ event.getOriginCommandRequestId() + " eventJson " + eventJson);
			loginMsEventProducer.sendBody(eventJson);

		} catch (CamelExecutionException e) {
			System.out.println("LoginMs : Command : Error in sending event to the queue " + event.getEventName());
			e.printStackTrace();
		}
	}

}
