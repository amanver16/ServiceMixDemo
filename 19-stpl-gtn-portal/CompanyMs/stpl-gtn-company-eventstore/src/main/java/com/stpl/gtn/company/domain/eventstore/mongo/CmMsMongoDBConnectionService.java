package com.stpl.gtn.company.domain.eventstore.mongo;

import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.event.data.CompanyMsAggregateDao;
import com.stpl.gtn.company.domain.event.data.CompanyMsAggregateEventDao;
import com.stpl.gtn.company.domain.eventstore.constant.CmMsEventStoreConstants;

public class CmMsMongoDBConnectionService {

	private static MongoDatabase dataBase = null;
	private CmMsMongoDBConnectionBean connectionBean;

	public CmMsMongoDBConnectionService(final CmMsMongoDBConnectionBean connectionBean) {
		super();
		this.connectionBean = connectionBean;
		getDataBaseInstance(connectionBean);
	}

	private static MongoDatabase getDataBaseInstance(final CmMsMongoDBConnectionBean connectionBean) {
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

	private MongoCollection<Document> getCompanyAggregateCollection() {
		return dataBase.getCollection("COMPANY_MASTER_AGGREGATE");
	}

	private MongoCollection<Document> getCompanyAggregateEventCollection() {
		return dataBase.getCollection("COMPANY_MASTER_AGGREGATE_EVENT");
	}

	public int checkForDuplicateAggregate(String aggregateId) {
		MongoCollection<Document> collection = getCompanyAggregateCollection();
		return (int) collection.count(new Document("AggregateId", aggregateId));
	}

	public void addAggregate(CompanyMsGenericEvent event) {
		MongoCollection<Document> collection = getCompanyAggregateCollection();
		Document cmDoc = new Document();
		cmDoc.put("AggregateId", event.getAggregateId());
		cmDoc.put("AggregateType", CmMsEventStoreConstants.COMPANY_MASTER);
		collection.insertOne(cmDoc);
	}

	public void addAggregateEvent(CompanyMsGenericEvent event, String content) {
		MongoCollection<Document> collection = getCompanyAggregateEventCollection();
		Document cmAggregateEvntdocument = new Document();
		cmAggregateEvntdocument.put("AggregateId", event.getAggregateId());
		cmAggregateEvntdocument.put("EventData", content);
		cmAggregateEvntdocument.put("EventName", event.getEventName());
		cmAggregateEvntdocument.put("Version", event.getVersion());
		collection.insertOne(cmAggregateEvntdocument);
	}

	public CompanyMsAggregateDao getCompanyAggregateEvents(String aggregateId) {

		CompanyMsAggregateDao aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Comppany MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new CompanyMsAggregateDao();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out.println("Cm MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private CompanyMsAggregateDao getAggregate(String aggregateId) {
		CompanyMsAggregateDao aggregate = null;
		try {
			MongoCollection<Document> collection = getCompanyAggregateCollection();
			MongoCursor<Document> results = collection.find(new Document("AggregateId", aggregateId))
					.sort(new Document("CreatedDate", 1)).iterator();

			while (results.hasNext()) {
				Document resultData = results.next();
				aggregate = new CompanyMsAggregateDao();
				aggregate.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				aggregate.setAggregateType(String.valueOf(resultData.get("AggregateType")));
			}
			results.close();
		} catch (Exception e) {
			System.out.println(
					"Cm MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, CompanyMsAggregateDao aggregate) throws SQLException {
		try {

			MongoCollection<Document> collection = getCompanyAggregateEventCollection();
			MongoCursor<Document> results = collection.find(new Document("AggregateId", aggregateId))
					.sort(new Document("CreatedDate", 1)).iterator();

			CompanyMsAggregateEventDao aggregateEvent = null;
			while (results.hasNext()) {
				Document eventAggregateResult = results.next();
				aggregateEvent = new CompanyMsAggregateEventDao();
				aggregateEvent.setEventData(String.valueOf(eventAggregateResult.get("EventData")));
				aggregateEvent.setEventName(String.valueOf(eventAggregateResult.get("EventName")));
				aggregateEvent.setVersion(String.valueOf(eventAggregateResult.get("Version")));
				aggregate.addAggregateEvent(aggregateEvent);
			}
			results.close();

		} catch (Exception e) {
			System.out.println(
					"Cm MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
		}

	}

}
