package com.stpl.gtn.item.domain.aggregate.item.loader;

import java.io.IOException;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.item.domain.aggregate.item.ItemMsItemAggregate;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.data.ItemMsAggregateDao;
import com.stpl.gtn.item.domain.event.data.ItemMsAggregateEventDao;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsAggregateLoader {
	@Produce(uri = "direct-vm:getItemAggregateEvents")
	ProducerTemplate itemMsEventStoreConsumer;

	public ItemMsItemAggregate loadAggregate(String aggregateId) throws IOException {

		ItemMsItemAggregate itemMsItemAggregate = new ItemMsItemAggregate(aggregateId);

		ItemMsAggregateDao aggregateFromDB = loadAggregateFromEventStore(aggregateId);

		if (aggregateFromDB.isAggregateNotFound() == false) {

			if (aggregateFromDB.getEventCount() > 0) {

				List<ItemMsAggregateEventDao> aggregateEventList = aggregateFromDB.getAggregateEventList();

				for (ItemMsAggregateEventDao currentEvent : aggregateEventList) {

					ItemMsGenericEvent currentGenericEvent = ItemMsJsonService
							.buildEventFromJson(currentEvent.getEventData());
					itemMsItemAggregate.applyEvent(currentGenericEvent);

				}

				System.out.println(
						"ItemMS: AggregateLoader: loaded " + aggregateEventList.size() + " events from event store");

			}
		}

		return itemMsItemAggregate;

	}

	private ItemMsAggregateDao loadAggregateFromEventStore(String aggregateId) {

		ItemMsAggregateDao itemMsAggregateDao = (ItemMsAggregateDao) itemMsEventStoreConsumer.requestBody(aggregateId);
		return itemMsAggregateDao;
	}

}
