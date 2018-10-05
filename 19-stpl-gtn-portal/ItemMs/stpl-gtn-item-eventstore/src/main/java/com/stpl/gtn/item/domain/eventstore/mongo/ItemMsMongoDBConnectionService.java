package com.stpl.gtn.item.domain.eventstore.mongo;

import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.data.ItemMsAggregateDao;
import com.stpl.gtn.item.domain.event.data.ItemMsAggregateEventDao;
import com.stpl.gtn.item.domain.eventstore.constant.ItemMsEventStoreConstants;

public class ItemMsMongoDBConnectionService {

	private static MongoDatabase dataBase = null;
	private ItemMsMongoDBConnectionBean connectionBean;

	public ItemMsMongoDBConnectionService(final ItemMsMongoDBConnectionBean connectionBean) {
		super();
		this.connectionBean = connectionBean;
		getDataBaseInstance(connectionBean);
	}

	private static MongoDatabase getDataBaseInstance(final ItemMsMongoDBConnectionBean connectionBean) {
		if (dataBase == null) {
			MongoClientURI connectionString = new MongoClientURI(
					"mongodb://" + connectionBean.getHost() + ":" + connectionBean.getPortNo());
			MongoClient mongoClient = new MongoClient(connectionString);
			dataBase = mongoClient.getDatabase(connectionBean.getDatabaseName());
		}
		return dataBase;
	}

	public static MongoDatabase getDBInstance() {
		return dataBase;
	}

	private MongoCollection<Document> getItemAggregateCollection() {
		return dataBase.getCollection("ITEM_MASTER_AGGREGATE");
	}

	private MongoCollection<Document> getItemAggregateEventCollection() {
		return dataBase.getCollection("ITEM_MASTER_AGGREGATE_EVENT");
	}

	public int checkForDuplicateAggregate(String aggregateId) {
		MongoCollection<Document> collection = getItemAggregateCollection();
		return (int) collection.count(new Document("AggregateId", aggregateId));
	}

	public void addAggregate(ItemMsGenericEvent event) {
		MongoCollection<Document> collection = getItemAggregateCollection();
		Document itemDoc = new Document();
		itemDoc.put("AggregateId", event.getAggregateId());
		itemDoc.put("AggregateType", ItemMsEventStoreConstants.ITEM_MASTER);
		collection.insertOne(itemDoc);
	}

	public void addAggregateEvent(ItemMsGenericEvent event, String content) {
		MongoCollection<Document> collection = getItemAggregateEventCollection();
		Document itemAggregateEvntdocument = new Document();
		itemAggregateEvntdocument.put("AggregateId", event.getAggregateId());
		itemAggregateEvntdocument.put("EventData", content);
		itemAggregateEvntdocument.put("EventName", event.getEventName());
		itemAggregateEvntdocument.put("Version", event.getVersion());
		collection.insertOne(itemAggregateEvntdocument);
	}

	public ItemMsAggregateDao getItemAggregateEvents(String aggregateId) {

		ItemMsAggregateDao aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Item MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new ItemMsAggregateDao();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out
					.println("Item MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private ItemMsAggregateDao getAggregate(String aggregateId) {
		ItemMsAggregateDao aggregate = null;
		try {
			MongoCollection<Document> collection = getItemAggregateCollection();
			MongoCursor<Document> results = collection.find(new Document("AggregateId", aggregateId))
					.sort(new Document("CreatedDate", 1)).iterator();

			while (results.hasNext()) {
				Document resultData = results.next();
				aggregate = new ItemMsAggregateDao();
				aggregate.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				aggregate.setAggregateType(String.valueOf(resultData.get("AggregateType")));
			}
			results.close();
		} catch (Exception e) {
			System.out.println(
					"Item MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, ItemMsAggregateDao aggregate) throws SQLException {
		try {

			MongoCollection<Document> collection = getItemAggregateEventCollection();
			MongoCursor<Document> results = collection.find(new Document("AggregateId", aggregateId))
					.sort(new Document("CreatedDate", 1)).iterator();

			ItemMsAggregateEventDao aggregateEvent = null;
			while (results.hasNext()) {
				Document eventAggregateResult = results.next();
				aggregateEvent = new ItemMsAggregateEventDao();
				aggregateEvent.setEventData(String.valueOf(eventAggregateResult.get("EventData")));
				aggregateEvent.setEventName(String.valueOf(eventAggregateResult.get("EventName")));
				aggregateEvent.setVersion(String.valueOf(eventAggregateResult.get("Version")));
				aggregate.addAggregateEvent(aggregateEvent);
			}
			results.close();

		} catch (Exception e) {
			System.out.println(
					"Item MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
		}

	}

}
