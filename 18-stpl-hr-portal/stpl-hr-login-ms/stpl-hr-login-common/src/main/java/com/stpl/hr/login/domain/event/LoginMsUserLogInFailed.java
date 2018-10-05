package com.stpl.hr.login.domain.event;

public class LoginMsUserLogInFailed {

	private String userName;
	private String failureReason;
	private int noOfInvalidAttempts;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public int getNoOfInvalidAttempts() {
		return noOfInvalidAttempts;
	}

	public void setNoOfInvalidAttempts(int noOfInvalidAttempts) {
		this.noOfInvalidAttempts = noOfInvalidAttempts;
	}

	
}
