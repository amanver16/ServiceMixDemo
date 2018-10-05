package com.stpl.hr.asset.domain.event.store.mongo.service;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.event.data.AssetMsAggregateDao;
import com.stpl.hr.asset.domain.event.data.AssetMsAggregateEventDao;
import com.stpl.hr.asset.domain.event.store.constants.AssetMsEventStoreConstant;

public class AssetMSEventStoreMongoService {
	private DB mongoDb;

	public AssetMSEventStoreMongoService(AssetMsMongoDbConnection assetMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(assetMsMongoDbConnection.getHost(),
					Integer.valueOf(assetMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(assetMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_ASSET_AGGREGATE", null);
			mongoDb.createCollection("THR_ASSET_AGGREGATE_EVENT", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Event Store-Write Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getAssetAggregateCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ASSET_AGGREGATE");
		}
		return null;
	}

	public DBCollection getAssetAggregateEventCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ASSET_AGGREGATE_EVENT");
		}
		return null;
	}

	public void dropAssetEventCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_ASSET_AGGREGATE").drop();
			mongoDb.getCollection("THR_ASSET_AGGREGATE_EVENT").drop();
		}
	}

	public int checkForDuplicateAggregate(String aggregateId) {
		try {
			DBCollection collection = getAssetAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			long count = collection.count(dbObject);
			return Integer.valueOf(Long.toString(count));
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Event Store-Write Model : Error in checkForDuplicateAggregate ");
			e.printStackTrace();
		}
		return 0;
	}

	public void addAggregate(AssetMsGenericEvent aggregateData) {
		try {
			DBCollection collection = getAssetAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateData.getAggregateId());
			dbObject.put("AggregateType", AssetMsEventStoreConstant.ASSET);
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Asset MS Event Store-Write Model : Error in addAggregate ");
			e.printStackTrace();
		}
	}

	public void addAggregateEvent(AssetMsGenericEvent aggregateEventData, String content) throws SQLException {
		try {
			DBCollection collection = getAssetAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateEventData.getAggregateId());
			dbObject.put("EventData", content);
			dbObject.put("EventName", aggregateEventData.getEventName());
			dbObject.put("Version", aggregateEventData.getVersion());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Asset MS Event Store-Write Model : Error in addAggregateEvent ");
			e.printStackTrace();
		}

	}

	public AssetMsAggregateDao getAssetAggregateEvents(String aggregateId) {

		AssetMsAggregateDao aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Asset MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new AssetMsAggregateDao();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out
					.println("Asset MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private AssetMsAggregateDao getAggregate(String aggregateId) {
		AssetMsAggregateDao aggregate = null;
		try {
			DBCollection collection = getAssetAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			if (cursor.hasNext()) {
				DBObject resultData = cursor.next();
				aggregate = new AssetMsAggregateDao();
				aggregate.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				aggregate.setAggregateType(String.valueOf(resultData.get("AggregateType")));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Asset MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, AssetMsAggregateDao aggregate) throws SQLException {
		try {

			DBCollection collection = getAssetAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			AssetMsAggregateEventDao aggregateEvent = null;
			while (cursor.hasNext()) {
				DBObject eventAggregateResult = cursor.next();
				aggregateEvent = new AssetMsAggregateEventDao();
				aggregateEvent.setEventData(String.valueOf(eventAggregateResult.get("EventData")));
				aggregateEvent.setEventName(String.valueOf(eventAggregateResult.get("EventName")));
				aggregateEvent.setVersion(String.valueOf(eventAggregateResult.get("Version")));
				aggregate.addAggregateEvent(aggregateEvent);
			}

		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Asset MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
		}

	}

}
