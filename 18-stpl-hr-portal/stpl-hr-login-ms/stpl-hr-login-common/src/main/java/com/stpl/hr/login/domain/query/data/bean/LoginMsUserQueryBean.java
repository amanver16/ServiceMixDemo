package com.stpl.hr.login.domain.query.data.bean;

public class LoginMsUserQueryBean {
	private String aggregateId;
	private String userName;

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "LoginMsUserQueryBean [aggregateId=" + aggregateId + ", userName=" + userName + "]";
	}

}
