package com.stpl.hr.asset.domain.query.mongo.service;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.stpl.hr.asset.common.constant.AssetMsCommonConstant;
import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;

public class AssetMSDomainQueryMongoDbService {

	private DB mongoDb;

	public AssetMSDomainQueryMongoDbService(AssetMsMongoDbConnection assetMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(assetMsMongoDbConnection.getHost(),
					Integer.valueOf(assetMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(assetMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_ASSET_QUERY_ALLOCATION", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Event Store-Write Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getAssetQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ASSET_QUERY_ALLOCATION");
		}
		return null;
	}

	public void dropAssetQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_ASSET_QUERY_ALLOCATION").drop();
		}
	}

	public void insertAsset(AssetMsAssetInventoryBean assetInventryData) throws SQLException, ParseException {

		try {
			DBCollection collection = getAssetQueryCollection();
			DBObject dbObject = new BasicDBObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			dbObject.put("AssetId", assetInventryData.getAssetId());
			dbObject.put("AssetAggregateId", assetInventryData.getAssetAggregateId());
			dbObject.put("AssetType", assetInventryData.getAssetType());
			dbObject.put("AssetSepcification", assetInventryData.getAssetSepcification());
			dbObject.put("AssetStatus", assetInventryData.getAssetStatus());
			dbObject.put("AssetOwner", assetInventryData.getAssetOwner());
			dbObject.put("AssetOwnerId", assetInventryData.getAssetOwnerId());
			if (assetInventryData.getAssetStartDate() != null) {
				dbObject.put("AssetStartDate", dateFormat.parse(assetInventryData.getAssetStartDate()));
			}
			if (assetInventryData.getAssetEndDate() != null) {
				dbObject.put("AssetEndDate", dateFormat.parse(assetInventryData.getAssetEndDate()));
			}

			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Asset MS Domain query Model : Error in insertAsset ");
			e.printStackTrace();
		}

	}

	public void allocateAsset(AssetMsAssetInventoryBean assetInventryData) throws SQLException, ParseException {

		try {
			DBCollection collection = getAssetQueryCollection();
			DBObject queryObject = new BasicDBObject();
			queryObject.put("AssetId", assetInventryData.getAssetId());
			DBObject dbObject = collection.find(queryObject).next();
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);

			dbObject.put("AssetStatus", assetInventryData.getAssetStatus());
			dbObject.put("AssetOwner", assetInventryData.getAssetOwner());
			dbObject.put("AssetOwnerId", assetInventryData.getAssetOwnerId());
			if (assetInventryData.getAssetStartDate() != null) {
				dbObject.put("AssetStartDate", dateFormat.parse(assetInventryData.getAssetStartDate()));
			}

			collection.update(queryObject, dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Asset MS Domain query Model : Error in allocateAsset ");
			e.printStackTrace();
		}
	}

	public String getVacantAsset(String assetType) throws SQLException {
		String vacantAssetAggregate = AssetMsCommonConstant.NO_VACANT_AGGREGATE;
		try {
			DBCollection collection;
			collection = getAssetQueryCollection();

			DBObject queryObject = new BasicDBObject();
			queryObject.put("AssetType", assetType);
			queryObject.put("AssetStatus", AssetMsCommonConstant.VACANT);

			DBCursor cursor = collection.find(queryObject).limit(1);
			if (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				vacantAssetAggregate = String.valueOf(resultObject.get("AssetAggregateId"));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Domain query Model : Error in getVacantAsset ");
			e.printStackTrace();
		}
		return vacantAssetAggregate;
	}

	public boolean isDuplicateAssetId(String assetId) throws SQLException {
		boolean isDuplicateAssetId = false;
		try {
			DBCollection collection;
			collection = getAssetQueryCollection();

			DBObject queryObject = new BasicDBObject();
			queryObject.put("AssetId", assetId);

			long count = collection.count(queryObject);
			isDuplicateAssetId = !(count == 0);

		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Domain query Model : Error in getVacantAsset ");
			e.printStackTrace();
		}
		return isDuplicateAssetId;
	}

	public AssetMsAssetInventoryBean getAssetByAggregateId(String aggtegateId) throws SQLException {
		AssetMsAssetInventoryBean assetInventryBean = null;
		try {
			DBCollection collection;
			collection = getAssetQueryCollection();

			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			DBObject queryObject = new BasicDBObject();
			queryObject.put("AssetAggregateId", aggtegateId);
			DBCursor cursor = collection.find(queryObject);

			if (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				assetInventryBean = new AssetMsAssetInventoryBean();
				assetInventryBean.setAssetId(String.valueOf(resultObject.get("AssetId")));
				assetInventryBean.setAssetAggregateId(String.valueOf(resultObject.get("AssetAggregateId")));
				assetInventryBean.setAssetType(String.valueOf(resultObject.get("AssetType")));
				assetInventryBean.setAssetSepcification(String.valueOf(resultObject.get("AssetSepcification")));
				assetInventryBean.setAssetStatus(String.valueOf(resultObject.get("AssetStatus")));
				assetInventryBean.setAssetOwner(String.valueOf(resultObject.get("AssetOwner")));
				String startDate = null;
				if (resultObject.get("AssetStartDate") != null) {
					startDate = dateFormat.format((Date) resultObject.get("AssetStartDate"));
				}
				assetInventryBean.setAssetStartDate(startDate);

				String endDate = null;
				if (resultObject.get("AssetEndDate") != null) {
					endDate = dateFormat.format((Date) resultObject.get("AssetEndDate"));
				}
				assetInventryBean.setAssetEndDate(endDate);
			}

		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Domain query Model : Error in getAssetByAggregateId ");
			e.printStackTrace();
		} finally {

		}
		return assetInventryBean;
	}

	public List<AssetMsAssetInventoryBean> getAllAsset() throws SQLException {
		List<AssetMsAssetInventoryBean> assetInventryBeanList = new ArrayList<>();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			DBCollection collection;
			collection = getAssetQueryCollection();
			DBCursor cursor = collection.find();
			while (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				AssetMsAssetInventoryBean assetInventryBean = new AssetMsAssetInventoryBean();
				assetInventryBean = new AssetMsAssetInventoryBean();
				assetInventryBean.setAssetId(String.valueOf(resultObject.get("AssetId")));
				assetInventryBean.setAssetAggregateId(String.valueOf(resultObject.get("AssetAggregateId")));
				assetInventryBean.setAssetType(String.valueOf(resultObject.get("AssetType")));
				assetInventryBean.setAssetSepcification(String.valueOf(resultObject.get("AssetSepcification")));
				assetInventryBean.setAssetStatus(String.valueOf(resultObject.get("AssetStatus")));
				assetInventryBean.setAssetOwner(String.valueOf(resultObject.get("AssetOwner")));
				String startDate = null;
				if (resultObject.get("AssetStartDate") != null) {
					startDate = dateFormat.format((Date) resultObject.get("AssetStartDate"));
				}
				assetInventryBean.setAssetStartDate(startDate);

				String endDate = null;
				if (resultObject.get("AssetEndDate") != null) {
					endDate = dateFormat.format((Date) resultObject.get("AssetEndDate"));
				}
				assetInventryBean.setAssetEndDate(endDate);
				assetInventryBeanList.add(assetInventryBean);
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Asset MS Domain query Model : Error in getAllAsset ");
			e.printStackTrace();
		}
		return assetInventryBeanList;
	}

}
