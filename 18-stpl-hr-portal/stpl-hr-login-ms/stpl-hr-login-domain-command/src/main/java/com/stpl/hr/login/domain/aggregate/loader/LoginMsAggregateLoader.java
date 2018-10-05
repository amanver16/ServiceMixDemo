package com.stpl.hr.login.domain.aggregate.loader;

import java.io.IOException;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.login.domain.aggregate.LoginMsUserAggregate;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.data.LoginMsAggregate;
import com.stpl.hr.login.domain.event.data.LoginMsAggregateEvent;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

@Component
public class LoginMsAggregateLoader {

	@Produce(uri = "direct-vm:getAggregateEvents")
	ProducerTemplate loginMsEventStoreConsumer;

	public LoginMsUserAggregate loadAggregate(String aggregateId) throws IOException {

		LoginMsUserAggregate loginMsUserAggregate = new LoginMsUserAggregate(aggregateId);

		LoginMsAggregate aggregateFromDB = loadAggregateFromEventStore(aggregateId);

		if (aggregateFromDB.isAggregateNotFound() == false) {

			if (aggregateFromDB.getEventCount() > 0) {

				List<LoginMsAggregateEvent> aggregateEventList = aggregateFromDB.getAggregateEventList();

				for (LoginMsAggregateEvent currentEvent : aggregateEventList) {

					LoginMsGenericEvent currentGenericEvent = LoginMSJsonService
							.buildEventFromJson(currentEvent.getEventData());
					loginMsUserAggregate.applyEvent(currentGenericEvent);

				}

				System.out.println("LoginMs : Command : AggregateLoader: loaded " + aggregateEventList.size()
						+ " events from event store");

			}
		}

		return loginMsUserAggregate;

	}

	private LoginMsAggregate loadAggregateFromEventStore(String aggregateId) {

		LoginMsAggregate loginMsAggregate = (LoginMsAggregate) loginMsEventStoreConsumer.requestBody(aggregateId);
		return loginMsAggregate;
	}
}
