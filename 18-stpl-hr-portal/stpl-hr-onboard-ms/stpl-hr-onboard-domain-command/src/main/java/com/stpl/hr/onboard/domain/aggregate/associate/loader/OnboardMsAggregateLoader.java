package com.stpl.hr.onboard.domain.aggregate.associate.loader;

import java.io.IOException;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.onboard.domain.aggregate.associate.OnboardMsAssociateAggregate;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateDao;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateEventDao;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsAggregateLoader {

	@Produce(uri = "direct-vm:getOnboardAggregateEvents")
	ProducerTemplate onboardMsEventStoreConsumer;

	public OnboardMsAssociateAggregate loadAggregate(String aggregateId) throws IOException {

		OnboardMsAssociateAggregate onboardMsAssociateAggregate = new OnboardMsAssociateAggregate(aggregateId);

		OnboardMsAggregateDao aggregateFromDB = loadAggregateFromEventStore(aggregateId);

		if (aggregateFromDB.isAggregateNotFound() == false) {

			if (aggregateFromDB.getEventCount() > 0) {

				List<OnboardMsAggregateEventDao> aggregateEventList = aggregateFromDB.getAggregateEventList();

				for (OnboardMsAggregateEventDao currentEvent : aggregateEventList) {

					OnboardMsGenericEvent currentGenericEvent = OnboardMSJsonService
							.buildEventFromJson(currentEvent.getEventData());
					onboardMsAssociateAggregate.applyEvent(currentGenericEvent);

				}

				System.out.println(
						"OnboardMS: AggregateLoader: loaded " + aggregateEventList.size() + " events from event store");

			}
		}

		return onboardMsAssociateAggregate;

	}

	private OnboardMsAggregateDao loadAggregateFromEventStore(String aggregateId) {

		OnboardMsAggregateDao onboardMsAggregateDao = (OnboardMsAggregateDao) onboardMsEventStoreConsumer
				.requestBody(aggregateId);
		return onboardMsAggregateDao;
	}

}
