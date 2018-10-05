package com.stpl.hr.asset.domain.aggregate.asset.loader;

import java.io.IOException;
import java.util.List;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.asset.domain.aggregate.asset.AssetMsAssetAggregate;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.event.data.AssetMsAggregateDao;
import com.stpl.hr.asset.domain.event.data.AssetMsAggregateEventDao;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsAggregateLoader {

	@Produce(uri = "direct-vm:getAssetAggregateEvents")
	ProducerTemplate assetMsEventStoreConsumer;

	public AssetMsAssetAggregate loadAggregate(String aggregateId) throws IOException {

		AssetMsAssetAggregate assetMsAssetAggregate = new AssetMsAssetAggregate(aggregateId);

		AssetMsAggregateDao aggregateFromDB = loadAggregateFromEventStore(aggregateId);

		if (aggregateFromDB.isAggregateNotFound() == false) {

			if (aggregateFromDB.getEventCount() > 0) {

				List<AssetMsAggregateEventDao> aggregateEventList = aggregateFromDB.getAggregateEventList();

				for (AssetMsAggregateEventDao currentEvent : aggregateEventList) {

					AssetMsGenericEvent currentGenericEvent = AssetMSJsonService
							.buildEventFromJson(currentEvent.getEventData());
					assetMsAssetAggregate.applyEvent(currentGenericEvent);

				}

				System.out.println(
						"AssetMS: AggregateLoader: loaded " + aggregateEventList.size() + " events from event store");

			}
		}

		return assetMsAssetAggregate;

	}

	private AssetMsAggregateDao loadAggregateFromEventStore(String aggregateId) {

		AssetMsAggregateDao assetMsAggregateDao = (AssetMsAggregateDao) assetMsEventStoreConsumer
				.requestBody(aggregateId);
		return assetMsAggregateDao;
	}

}
