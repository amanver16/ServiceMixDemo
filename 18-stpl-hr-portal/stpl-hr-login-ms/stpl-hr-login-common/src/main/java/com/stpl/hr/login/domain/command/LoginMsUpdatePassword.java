package com.stpl.hr.login.domain.command;

public class LoginMsUpdatePassword {

	private String userName;
	private String oldPassword;
	private String newPassword;
	private String passwordUpdateMessage;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getPasswordUpdateMessage() {
		return passwordUpdateMessage;
	}

	public void setPasswordUpdateMessage(String passwordUpdateMessage) {
		this.passwordUpdateMessage = passwordUpdateMessage;
	}

}
