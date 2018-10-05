package com.stpl.hr.login.domain.query.mongo.service;

import java.net.UnknownHostException;
import java.sql.SQLException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.stpl.hr.login.domain.query.data.bean.LoginMsUserQueryBean;

public class LoginMSDomainQueryMongoService {

	private DB mongoDb;

	public LoginMSDomainQueryMongoService(LoginMsMongoDbConnection loginMsMongoDbConnection) {
		try {
			Mongo mongo = new Mongo(loginMsMongoDbConnection.getHost(),
					Integer.valueOf(loginMsMongoDbConnection.getPort()));
			mongoDb = mongo.getDB(loginMsMongoDbConnection.getDb());
			mongoDb.createCollection("THR_LOGIN_QUERY_USER", null);
		} catch (UnknownHostException | MongoException e) {
			System.out.println("Login MS Quert Model : Error in MongoDb Connection ");
			e.printStackTrace();
		}
	}

	public DBCollection getLoginQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			return mongoDb.getCollection("THR_LOGIN_QUERY_USER");
		}
		return null;
	}

	public void dropLoginQueryCollection() throws UnknownHostException, MongoException {
		if (mongoDb != null) {
			mongoDb.getCollection("THR_LOGIN_QUERY_USER").drop();
		}
	}

	public LoginMsUserQueryBean getAggregateIdByUserName(String userName) throws SQLException {
		LoginMsUserQueryBean resultBean = null;

		try {
			DBCollection collection = getLoginQueryCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("UserName", userName);
			DBCursor cursor = collection.find(dbObject);

			if (cursor.hasNext()) {
				DBObject resultData = cursor.next();
				resultBean = new LoginMsUserQueryBean();
				resultBean.setAggregateId(String.valueOf(resultData.get("AggregateId")));
				resultBean.setUserName(String.valueOf(resultData.get("UserName")));
			}

		} catch (MongoException | UnknownHostException e) {
			System.out.println("Login MS Event Query Model : Error in getAggregateIdByUserName ");
			e.printStackTrace();
		}

		return resultBean;
	}

	public void addUser(LoginMsUserQueryBean inputUserBean) throws SQLException {

		try {
			DBCollection collection = getLoginQueryCollection();
			DBObject dbObject = new BasicDBObject();
			dbObject.put("AggregateId", inputUserBean.getAggregateId());
			dbObject.put("UserName", inputUserBean.getUserName());
			collection.insert(dbObject);
		} catch (MongoException | UnknownHostException e) {
			System.out.println("Login MS Event Query Model : Error in addUser ");
			e.printStackTrace();
		}
	}

}
