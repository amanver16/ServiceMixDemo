package com.stpl.gtn.company.domain.aggregate.company.loader;

import java.io.IOException;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.company.domain.aggregate.company.CompanyMsCompanyAggregate;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.event.data.CompanyMsAggregateDao;
import com.stpl.gtn.company.domain.event.data.CompanyMsAggregateEventDao;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsAggregateLoader {
	@Produce(uri = "direct-vm:getCompanyAggregateEvents")
	ProducerTemplate companyMsEventStoreConsumer;

	public CompanyMsCompanyAggregate loadAggregate(String aggregateId) throws IOException {

		CompanyMsCompanyAggregate companyMsCompanyAggregate = new CompanyMsCompanyAggregate(aggregateId);

		CompanyMsAggregateDao aggregateFromDB = loadAggregateFromEventStore(aggregateId);

		if (aggregateFromDB.isAggregateNotFound() == false) {

			if (aggregateFromDB.getEventCount() > 0) {

				List<CompanyMsAggregateEventDao> aggregateEventList = aggregateFromDB.getAggregateEventList();

				for (CompanyMsAggregateEventDao currentEvent : aggregateEventList) {

					CompanyMsGenericEvent currentGenericEvent = CompanyMsJsonService
							.buildEventFromJson(currentEvent.getEventData());
					companyMsCompanyAggregate.applyEvent(currentGenericEvent);

				}

				System.out.println(
						"CompanyMS: AggregateLoader: loaded " + aggregateEventList.size() + " events from event store");

			}
		}

		return companyMsCompanyAggregate;

	}

	private CompanyMsAggregateDao loadAggregateFromEventStore(String aggregateId) {

		CompanyMsAggregateDao companyMsAggregateDao = (CompanyMsAggregateDao) companyMsEventStoreConsumer
				.requestBody(aggregateId);
		return companyMsAggregateDao;
	}

}
