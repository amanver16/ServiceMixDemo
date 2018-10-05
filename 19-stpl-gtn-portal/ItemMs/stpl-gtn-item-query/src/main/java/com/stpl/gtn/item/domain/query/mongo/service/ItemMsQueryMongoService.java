package com.stpl.gtn.item.domain.query.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsDomainConstant;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsSearchCriteria;

public class ItemMsQueryMongoService {

	private final MongoClient mongoClient;
	private final MongoDatabase mongoDb;

	public ItemMsQueryMongoService(ItemMsMongoConnectionBean connectionBean) {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		ServerAddress address = new ServerAddress(connectionBean.getHostName(), connectionBean.getPortNo());

		this.mongoClient = new MongoClient(address,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		this.mongoDb = this.mongoClient.getDatabase(connectionBean.getDbName());
	}

	public void destroy() {
		this.mongoClient.close();
	}

	public void insertItem(ItemMsItemBean itemBean) {

		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		collection.insertOne(itemBean);
	}

	public void deleteItem(ItemMsItemBean itemBean) {

		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		collection.deleteOne(new Document("aggregateId", itemBean.getAggregateId()));
	}

	public void updateItem(ItemMsItemBean itemBean) {

		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		collection.deleteOne(new Document("aggregateId", itemBean.getAggregateId()));
		collection.insertOne(itemBean);
	}

	public List<ItemMsItemBean> queryItem(List<ItemMsSearchCriteria> searchCriteriaList, int start, int offset) {
		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		Document searchInput = new Document();
		if (!searchCriteriaList.isEmpty()) {
			for (ItemMsSearchCriteria itemMsSearchCriteria : searchCriteriaList) {
				searchInput.append("itemInformationBean." + itemMsSearchCriteria.getFieldId(),
						itemMsSearchCriteria.getFilterValue1());
			}

			MongoIterable<ItemMsItemBean> results = collection.find(searchInput, ItemMsItemBean.class).skip(start)
					.limit(offset).batchSize(offset);
			List<ItemMsItemBean> itemBean = new ArrayList<>();
			for (ItemMsItemBean itemMsItemBean : results) {
				itemBean.add(itemMsItemBean);
			}
			return itemBean;
		}
		return null;

	}

	public long queryItemCount(List<ItemMsSearchCriteria> searchCriteriaList, int start, int offset) {
		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		Document searchInput = new Document();
		if (!searchCriteriaList.isEmpty()) {
			for (ItemMsSearchCriteria itemMsSearchCriteria : searchCriteriaList) {
				searchInput.append("itemInformationBean." + itemMsSearchCriteria.getFieldId(),
						itemMsSearchCriteria.getFilterValue1());
			}

			return collection.count(searchInput);
		}
		return 0l;
	}

	public List<String> getDomainConstantList(String domainConstantName) {
		MongoCollection<ItemMsDomainConstant> collection = mongoDb.getCollection("TGTN_DOMAIN_CONSTANT_QUERY",
				ItemMsDomainConstant.class);
		Document searchInput = new Document("domainConstantName", domainConstantName);
		MongoIterable<ItemMsDomainConstant> results = collection.find(searchInput, ItemMsDomainConstant.class);

		for (ItemMsDomainConstant itemMsDomainConstant : results) {
			return itemMsDomainConstant.getDomainConstantList();
		}

		return null;
	}

	public ItemMsItemBean getItemDetails(String aggregateId) {
		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		Document searchInput = new Document("aggregateId", aggregateId);
		MongoIterable<ItemMsItemBean> results = collection.find(searchInput, ItemMsItemBean.class);

		for (ItemMsItemBean itemMsItemBean : results) {
			return itemMsItemBean;
		}

		return null;
	}

	public boolean getDuplicateItem(String aggregateId) {
		MongoCollection<ItemMsItemBean> collection = mongoDb.getCollection("TGTN_ITEM_QUERY", ItemMsItemBean.class);
		Document searchInput = new Document("aggregateId", aggregateId);
		MongoIterable<ItemMsItemBean> results = collection.find(searchInput, ItemMsItemBean.class);
		if (results.iterator().hasNext()) {
			return true;
		}

		return false;
	}

}
