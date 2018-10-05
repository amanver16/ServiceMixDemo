package com.stpl.hr.login.domain.event;

public class LoginMsPasswordUpdated {

	private String status;
	private String userName;
	private String oldPassword;
	private String newPassword;
	private String passwordChangedStatus;

	public String getPasswordChangedStatus() {
		return passwordChangedStatus;
	}

	public void setPasswordChangedStatus(String passwordChangedStatus) {
		this.passwordChangedStatus = passwordChangedStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
