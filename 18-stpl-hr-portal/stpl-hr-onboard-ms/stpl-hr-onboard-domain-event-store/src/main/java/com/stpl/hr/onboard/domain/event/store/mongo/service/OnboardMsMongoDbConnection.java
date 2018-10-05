package com.stpl.hr.onboard.domain.event.store.mongo.service;

public class OnboardMsMongoDbConnection {
	private String host;
	private String port;
	private String db;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

}
