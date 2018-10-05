package com.stpl.gtn.item.domain.eventstore.mongo;

public class ItemMsMongoDBConnectionBean {

	private String host;
	private String portNo;
	private String databaseName;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

}
