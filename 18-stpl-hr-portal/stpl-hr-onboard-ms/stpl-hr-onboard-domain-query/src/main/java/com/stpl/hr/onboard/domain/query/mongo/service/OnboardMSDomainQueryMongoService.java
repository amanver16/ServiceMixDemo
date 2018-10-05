package com.stpl.hr.onboard.domain.query.mongo.service;

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
import com.stpl.hr.onboard.common.constant.OnboardMsCommonConstant;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;

public class OnboardMSDomainQueryMongoService {

	private DB mongoDb;

	public OnboardMSDomainQueryMongoService(OnboardMsMongoDbConnection onboardMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(onboardMsMongoDbConnection.getHost(),
					Integer.valueOf(onboardMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(onboardMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_ONBOARD_QUERY_ASSOCIATE", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getOnboardQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_ONBOARD_QUERY_ASSOCIATE");
		}
		return null;
	}

	public void dropOnboardQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_ONBOARD_QUERY_ASSOCIATE").drop();
		}
	}

	public void onboardAssociate(OnboardMsOnboardAssociateBean onboardData) {

		try {
			DBCollection collection = getOnboardQueryCollection();
			DBObject dbObject = new BasicDBObject();
			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			dbObject.put("AssociateId", Long.parseLong(onboardData.getAssociateId()));
			dbObject.put("AggregateId", onboardData.getAggregateId());
			dbObject.put("FirstName", onboardData.getFirstName());
			dbObject.put("LastName", onboardData.getLastName());
			dbObject.put("MobileNo", onboardData.getMobileNo());
			dbObject.put("EmailId", onboardData.getEmailId());
			dbObject.put("HighestDegree", onboardData.getHighestDegree());
			dbObject.put("Cgpa", onboardData.getCgpa());
			dbObject.put("CollegeName", onboardData.getCollegeName());
			dbObject.put("PreviousEmployer", onboardData.getPreviousEmployer());
			dbObject.put("YearOfExperience", onboardData.getYearOfExperience());
			dbObject.put("BusinessUnit", onboardData.getBusinessUnit());
			dbObject.put("Role", onboardData.getRole());
			dbObject.put("Designation", onboardData.getDesignation());
			if (onboardData.getJoiningDate() != null) {
				dbObject.put("JoiningDate", dateFormat.parse(onboardData.getJoiningDate()));
			}
			dbObject.put("UserName", onboardData.getUserName());
			dbObject.put("Status", onboardData.getStatus());
			dbObject.put("AssetInfo", onboardData.getAssetInfo());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException | ParseException e) {
			System.out.println("Onboard MS Domain query Model : Error in onBoardCandidate ");
			e.printStackTrace();
		}
	}

	public OnboardMsOnboardAssociateBean getAssociateDetails(String associateId) throws SQLException {
		OnboardMsOnboardAssociateBean associateDataBean = null;
		System.out.println("Onboard MS Domain query Model : associateId " + associateId);
		try {
			DBCollection collection;
			collection = getOnboardQueryCollection();

			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AssociateId", Long.parseLong(associateId));
			DBCursor cursor = collection.find(dbObject);

			if (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				associateDataBean = new OnboardMsOnboardAssociateBean();
				associateDataBean.setAssociateId(String.valueOf(resultObject.get("AssociateId")));
				associateDataBean.setAggregateId(String.valueOf(resultObject.get("AggregateId")));
				associateDataBean.setFirstName(String.valueOf(resultObject.get("FirstName")));
				associateDataBean.setLastName(String.valueOf(resultObject.get("LastName")));
				associateDataBean.setMobileNo(String.valueOf(resultObject.get("MobileNo")));
				associateDataBean.setEmailId(String.valueOf(resultObject.get("EmailId")));
				associateDataBean.setHighestDegree(String.valueOf(resultObject.get("HighestDegree")));
				associateDataBean.setCgpa(String.valueOf(resultObject.get("Cgpa")));
				associateDataBean.setCollegeName(String.valueOf(resultObject.get("CollegeName")));
				associateDataBean.setPreviousEmployer(String.valueOf(resultObject.get("PreviousEmployer")));
				associateDataBean.setYearOfExperience(String.valueOf(resultObject.get("YearOfExperience")));
				associateDataBean.setBusinessUnit(String.valueOf(resultObject.get("BusinessUnit")));
				associateDataBean.setRole(String.valueOf(resultObject.get("Role")));
				associateDataBean.setDesignation(String.valueOf(resultObject.get("Designation")));
				if (resultObject.get("JoiningDate") != null) {
					associateDataBean.setJoiningDate(dateFormat.format((Date) resultObject.get("JoiningDate")));
				}
				associateDataBean.setStatus(String.valueOf(resultObject.get("Status")));
				associateDataBean.setAssetInfo(String.valueOf(resultObject.get("AssetInfo")));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAssociateDetails ");
			e.printStackTrace();
		}
		return associateDataBean;
	}

	public List<OnboardMsOnboardAssociateBean> getAllAssociateDetails() throws SQLException {
		List<OnboardMsOnboardAssociateBean> associateDataBeanList = new ArrayList<>();
		try {
			DBCollection collection;
			collection = getOnboardQueryCollection();

			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			DBCursor cursor = collection.find();

			while (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				OnboardMsOnboardAssociateBean associateDataBean = new OnboardMsOnboardAssociateBean();
				associateDataBean = new OnboardMsOnboardAssociateBean();
				associateDataBean.setAssociateId(String.valueOf(resultObject.get("AssociateId")));
				associateDataBean.setAggregateId(String.valueOf(resultObject.get("AggregateId")));
				associateDataBean.setFirstName(String.valueOf(resultObject.get("FirstName")));
				associateDataBean.setLastName(String.valueOf(resultObject.get("LastName")));
				associateDataBean.setMobileNo(String.valueOf(resultObject.get("MobileNo")));
				associateDataBean.setEmailId(String.valueOf(resultObject.get("EmailId")));
				associateDataBean.setHighestDegree(String.valueOf(resultObject.get("HighestDegree")));
				associateDataBean.setCgpa(String.valueOf(resultObject.get("Cgpa")));
				associateDataBean.setCollegeName(String.valueOf(resultObject.get("CollegeName")));
				associateDataBean.setPreviousEmployer(String.valueOf(resultObject.get("PreviousEmployer")));
				associateDataBean.setYearOfExperience(String.valueOf(resultObject.get("YearOfExperience")));
				associateDataBean.setBusinessUnit(String.valueOf(resultObject.get("BusinessUnit")));
				associateDataBean.setRole(String.valueOf(resultObject.get("Role")));
				associateDataBean.setDesignation(String.valueOf(resultObject.get("Designation")));
				if (resultObject.get("JoiningDate") != null) {
					associateDataBean.setJoiningDate(dateFormat.format((Date) resultObject.get("JoiningDate")));
				}
				associateDataBean.setStatus(String.valueOf(resultObject.get("Status")));
				associateDataBean.setAssetInfo(String.valueOf(resultObject.get("AssetInfo")));
				associateDataBean.setUserName(String.valueOf(resultObject.get("UserName")));
				associateDataBeanList.add(associateDataBean);
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAllAssociateDetails ");
			e.printStackTrace();
		}
		return associateDataBeanList;
	}

	public long getLatestAssociateId() throws SQLException {
		long latestAssociateId = 10000L;
		try {
			DBCollection collection;
			collection = getOnboardQueryCollection();

			DBObject sort = new BasicDBObject();
			sort.put("AssociateId", -1);

			DBCursor cursor = collection.find().sort(sort).limit(1);
			if (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				latestAssociateId = Long.valueOf(String.valueOf(resultObject.get("AssociateId")));

			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Domain query Model : Error in getLatestAssociateId ");
			e.printStackTrace();
		}
		System.out.println("Onboard MS Domain query Model Getting the getLatestAssociateId-----" + latestAssociateId);
		return latestAssociateId;
	}

	public OnboardMsOnboardAssociateBean getAssociateDetailsByAggregateId(String aggregateId, String eventName)
			throws SQLException {
		OnboardMsOnboardAssociateBean associateDataBean = null;
		System.out.println("Onboard MS Domain query Model : aggregateId " + aggregateId);
		try {
			DBCollection collection;
			collection = getOnboardQueryCollection();

			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", aggregateId);
			dbObject.put("Status", eventName);
			DBCursor cursor = collection.find(dbObject);

			if (cursor.hasNext()) {
				DBObject resultObject = cursor.next();
				associateDataBean = new OnboardMsOnboardAssociateBean();
				associateDataBean.setAssociateId(String.valueOf(resultObject.get("AssociateId")));
				associateDataBean.setAggregateId(String.valueOf(resultObject.get("AggregateId")));
				associateDataBean.setFirstName(String.valueOf(resultObject.get("FirstName")));
				associateDataBean.setLastName(String.valueOf(resultObject.get("LastName")));
				associateDataBean.setMobileNo(String.valueOf(resultObject.get("MobileNo")));
				associateDataBean.setEmailId(String.valueOf(resultObject.get("EmailId")));
				associateDataBean.setHighestDegree(String.valueOf(resultObject.get("HighestDegree")));
				associateDataBean.setCgpa(String.valueOf(resultObject.get("Cgpa")));
				associateDataBean.setCollegeName(String.valueOf(resultObject.get("CollegeName")));
				associateDataBean.setPreviousEmployer(String.valueOf(resultObject.get("PreviousEmployer")));
				associateDataBean.setYearOfExperience(String.valueOf(resultObject.get("YearOfExperience")));
				associateDataBean.setBusinessUnit(String.valueOf(resultObject.get("BusinessUnit")));
				associateDataBean.setRole(String.valueOf(resultObject.get("Role")));
				associateDataBean.setDesignation(String.valueOf(resultObject.get("Designation")));
				if (resultObject.get("JoiningDate") != null) {
					associateDataBean.setJoiningDate(dateFormat.format((Date) resultObject.get("JoiningDate")));
				}
				associateDataBean.setStatus(String.valueOf(resultObject.get("Status")));
				associateDataBean.setAssetInfo(String.valueOf(resultObject.get("AssetInfo")));
			}
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAssociateDetails ");
			e.printStackTrace();
		}
		return associateDataBean;
	}
}
