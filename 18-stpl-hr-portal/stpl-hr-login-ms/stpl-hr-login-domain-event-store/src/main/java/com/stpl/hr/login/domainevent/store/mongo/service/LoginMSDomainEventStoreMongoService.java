package com.stpl.hr.login.domainevent.store.mongo.service;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.data.LoginMsAggregate;
import com.stpl.hr.login.domain.event.data.LoginMsAggregateEvent;

public class LoginMSDomainEventStoreMongoService {

	private DB mongoDb;

	public LoginMSDomainEventStoreMongoService(LoginMsMongoDbConnection loginMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(loginMsMongoDbConnection.getHost(),
					Integer.valueOf(loginMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(loginMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_LOGIN_AGGREGATE", null);
			mongoDb.createCollection("THR_LOGIN_AGGREGATE_EVENT", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Login MS Event Store-Write Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getLoginAggregateCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_LOGIN_AGGREGATE");
		}
		return null;
	}

	public DBCollection getLoginAggregateEventCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_LOGIN_AGGREGATE_EVENT");
		}
		return null;
	}

	public void dropLoginEventStoreCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_LOGIN_AGGREGATE").drop();
			mongoDb.getCollection("THR_LOGIN_AGGREGATE_EVENT").drop();
		}
	}

	public int checkForDuplicateAggregate(String aggregateId) {
		try {
			DBCollection collection = getLoginAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			long count = collection.count(dbObject);
			return Integer.valueOf(Long.toString(count));
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Login MS Event Store-Write Model : Error in checkForDuplicateAggregate ");
			e.printStackTrace();
		}
		return 0;
	}

	public void addLoginAggregate(LoginMsGenericEvent aggregateData) {
		try {
			DBCollection collection = getLoginAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateData.getAggregateId());
			dbObject.put("AggregateType", "User");
			dbObject.put("CreatedDate", new Date());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Login MS Event Store-Write Model : Error in addAggregate ");
			e.printStackTrace();
		}
	}

	public void addLoginAggregateEvent(LoginMsGenericEvent aggregateEventData, String content) throws SQLException {
		try {
			DBCollection collection = getLoginAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateEventData.getAggregateId());
			dbObject.put("EventData", content);
			dbObject.put("EventName", aggregateEventData.getEventName());
			dbObject.put("Version", aggregateEventData.getVersion());
			dbObject.put("CreatedDate", new Date());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Login MS Event Store-Write Model : Error in addAggregateEvent ");
			e.printStackTrace();
		}

	}

	public LoginMsAggregate getAggregateDetails(String aggregateId) {

		LoginMsAggregate aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Login MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new LoginMsAggregate();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out
					.println("Login MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private LoginMsAggregate getAggregate(String aggregateId) {
		LoginMsAggregate aggregate = null;
		try {
			DBCollection collection = getLoginAggregateCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);

			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			if (cursor.hasNext()) {
				DBObject resultData = cursor.next();
				aggregate = new LoginMsAggregate();
				aggregate.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				aggregate.setAggregateType(String.valueOf(resultData.get("AggregateType")));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Login MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, LoginMsAggregate aggregate) throws SQLException {
		try {

			DBCollection collection = getLoginAggregateEventCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			DBObject sort = new BasicDBObject();
			sort.put("CreatedDate", 1);
			DBCursor cursor = collection.find(dbObject).sort(sort);

			while (cursor.hasNext()) {
				DBObject eventAggregateResult = cursor.next();
				LoginMsAggregateEvent aggregateEvent = new LoginMsAggregateEvent();
				aggregateEvent.setEventData(String.valueOf(eventAggregateResult.get("EventData")));
				aggregateEvent.setEventName(String.valueOf(eventAggregateResult.get("EventName")));
				aggregateEvent.setVersion(String.valueOf(eventAggregateResult.get("Version")));
				aggregate.addAggregateEvent(aggregateEvent);
			}

		} catch (UnknownHostException | MongoException e) {
			System.out.println(
					"Login MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
		}

	}

}
