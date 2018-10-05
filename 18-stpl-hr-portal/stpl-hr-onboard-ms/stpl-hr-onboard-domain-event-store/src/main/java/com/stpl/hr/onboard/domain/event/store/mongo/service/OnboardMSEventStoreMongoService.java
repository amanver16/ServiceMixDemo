package com.stpl.hr.onboard.domain.event.store.mongo.service;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateDao;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateEventDao;
import com.stpl.hr.onboard.domain.event.store.constants.OnboardMsEventStoreConstant;

public class OnboardMSEventStoreMongoService {
	private DB mongoDb;

	public OnboardMSEventStoreMongoService(OnboardMsMongoDbConnection onboardMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(onboardMsMongoDbConnection.getHost(),
					Integer.valueOf(onboardMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(onboardMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_ONBOARD_AGGREGATE", null);
			mongoDb.createCollection("THR_ONBOARD_AGGREGATE_EVENT", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getOnboardAggregateCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ONBOARD_AGGREGATE");
		}
		return null;
	}

	public DBCollection getOnboardAggregateEventCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ONBOARD_AGGREGATE_EVENT");
		}
		return null;
	}

	public void dropOnboardEventCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_ONBOARD_AGGREGATE").drop();
			mongoDb.getCollection("THR_ONBOARD_AGGREGATE_EVENT").drop();
		}
	}

	public int checkForDuplicateAggregate(String aggregateId) {
		try {
			DBCollection collection = getOnboardAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			long count = collection.count(dbObject);
			return Integer.valueOf(Long.toString(count));
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in checkForDuplicateAggregate ");
			e.printStackTrace();
		}
		return 0;
	}

	public void addAggregate(OnboardMsGenericEvent aggregateData) {
		try {
			DBCollection collection = getOnboardAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateData.getAggregateId());
			dbObject.put("AggregateType", OnboardMsEventStoreConstant.ASSOCIATE);
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in addAggregate ");
			e.printStackTrace();
		}
	}

	public void addAggregateEvent(OnboardMsGenericEvent aggregateEventData, String content) throws SQLException {
		try {
			DBCollection collection = getOnboardAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateEventData.getAggregateId());
			dbObject.put("EventData", content);
			dbObject.put("EventName", aggregateEventData.getEventName());
			dbObject.put("Version", aggregateEventData.getVersion());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in addAggregateEvent ");
			e.printStackTrace();
		}

	}

	public OnboardMsAggregateDao getAssociateAggregateEvents(String aggregateId) {

		OnboardMsAggregateDao aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Onboard MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new OnboardMsAggregateDao();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out.println(
					"Onboard MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private OnboardMsAggregateDao getAggregate(String aggregateId) {
		OnboardMsAggregateDao aggregate = null;
		try {
			DBCollection collection = getOnboardAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			if (cursor.hasNext()) {
				DBObject resultData = cursor.next();
				aggregate = new OnboardMsAggregateDao();
				aggregate.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				aggregate.setAggregateType(String.valueOf(resultData.get("AggregateType")));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Onboard MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, OnboardMsAggregateDao aggregate) throws SQLException {
		try {

			DBCollection collection = getOnboardAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			OnboardMsAggregateEventDao aggregateEvent = null;
			while (cursor.hasNext()) {
				DBObject eventAggregateResult = cursor.next();
				aggregateEvent = new OnboardMsAggregateEventDao();
				aggregateEvent.setEventData(String.valueOf(eventAggregateResult.get("EventData")));
				aggregateEvent.setEventName(String.valueOf(eventAggregateResult.get("EventName")));
				aggregateEvent.setVersion(String.valueOf(eventAggregateResult.get("Version")));
				aggregate.addAggregateEvent(aggregateEvent);
			}

		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Onboard MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
		}

	}

}
