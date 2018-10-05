package com.stpl.hr.login.domain.aggregate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stpl.hr.login.command.aggregate.LoginMsAggregateType;
import com.stpl.hr.login.domain.command.LoginMsCheckUserCredentials;
import com.stpl.hr.login.domain.command.LoginMsCommandType;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.command.LoginMsRegisterNewUser;
import com.stpl.hr.login.domain.command.LoginMsUpdatePassword;
import com.stpl.hr.login.domain.event.LoginMSNewUserRegsitered;
import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsPasswordUpdateFailed;
import com.stpl.hr.login.domain.event.LoginMsPasswordUpdated;
import com.stpl.hr.login.domain.event.LoginMsUserLogInFailed;
import com.stpl.hr.login.domain.event.LoginMsUserLoggedIn;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMsUserAggregate {

	public LoginMsUserAggregate(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	private String aggregateId;
	private String userName;
	private String firstName;
	private String lastName;
	private String emailId;
	private String defaultPassword = "welcome";
	private String currentPassword;
	private List<String> oldPasswordList;
	private int noOfInvalidAttempts;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public List<String> getOldPasswordList() {
		return oldPasswordList;
	}

	public void setOldPasswordList(List<String> oldPasswordList) {
		this.oldPasswordList = oldPasswordList;
	}

	public int getNoOfInvalidAttempts() {
		return noOfInvalidAttempts;
	}

	public void setNoOfInvalidAttempts(int noOfInvalidAttempts) {
		this.noOfInvalidAttempts = noOfInvalidAttempts;
	}

	public void applyEvent(LoginMsGenericEvent eventToBeApplied) throws IOException {

		this.aggregateId = eventToBeApplied.getAggregateId();

		if (eventToBeApplied.getEventName().equals(LoginMsEventType.NewUserRegistered.getEventName())) {

			LoginMSNewUserRegsitered newUserRegisteredEvent = (LoginMSNewUserRegsitered) LoginMSJsonService
					.convertJsonToObject(LoginMSNewUserRegsitered.class, eventToBeApplied.getEventData());
			this.userName = newUserRegisteredEvent.getUserName();
			this.currentPassword = newUserRegisteredEvent.getPassword();
			this.firstName = newUserRegisteredEvent.getFirstName();
			this.lastName = newUserRegisteredEvent.getLastName();
			this.emailId = newUserRegisteredEvent.getEmailId();
			return;

		}

		if (eventToBeApplied.getEventName().equals(LoginMsEventType.PasswordUpdated.getEventName())) {

			LoginMsPasswordUpdated passwordUpdatedEvent = (LoginMsPasswordUpdated) LoginMSJsonService
					.convertJsonToObject(LoginMsPasswordUpdated.class, eventToBeApplied.getEventData());

			if (this.oldPasswordList == null) {
				oldPasswordList = new ArrayList<>();
			}
			this.oldPasswordList.add(this.currentPassword);
			this.currentPassword = passwordUpdatedEvent.getNewPassword();

		}

	}

	public List<LoginMsGenericEvent> handleCommand(LoginMsGenericCommand genericCommand) throws IOException {

		List<LoginMsGenericEvent> eventList = new ArrayList<>();

		if (genericCommand.getCommandName().equals(LoginMsCommandType.CheckUserCredentials.getCommandName())) {
			LoginMsCheckUserCredentials loginMsCheckUserCredentialsCommand = (LoginMsCheckUserCredentials) LoginMSJsonService
					.convertJsonToObject(LoginMsCheckUserCredentials.class, genericCommand.getCommandData());
			boolean loginSuccess = this.checkUserCredentials(loginMsCheckUserCredentialsCommand);
			if (loginSuccess) {
				this.addUserLoggedInEvent(eventList, genericCommand, loginMsCheckUserCredentialsCommand);
				this.resetInvalidAttempts();
			} else {
				this.incrementInvalidAttempts();
				this.addUserLoggedInFailedEvent(eventList, genericCommand, loginMsCheckUserCredentialsCommand);
			}

		}
		if (genericCommand.getCommandName().equals(LoginMsCommandType.RegisterNewUser.getCommandName())) {
			LoginMsRegisterNewUser loginMsRegisterNewUserCommand = (LoginMsRegisterNewUser) LoginMSJsonService
					.convertJsonToObject(LoginMsRegisterNewUser.class, genericCommand.getCommandData());
			this.userName = loginMsRegisterNewUserCommand.getFirstName() + "."
					+ loginMsRegisterNewUserCommand.getLastName();
			this.firstName = loginMsRegisterNewUserCommand.getFirstName();
			this.lastName = loginMsRegisterNewUserCommand.getLastName();
			this.emailId = loginMsRegisterNewUserCommand.getEmail();
			this.currentPassword = this.defaultPassword;
			this.addNewUserRegisteredEvent(eventList, genericCommand, loginMsRegisterNewUserCommand);

		}

		if (genericCommand.getCommandName().equals(LoginMsCommandType.UpdatePassword.getCommandName())) {
			LoginMsUpdatePassword loginMsUpdatePasswordCommand = (LoginMsUpdatePassword) LoginMSJsonService
					.convertJsonToObject(LoginMsUpdatePassword.class, genericCommand.getCommandData());
			boolean loginSuccess = this.checkPasswordUpdatePolicy(loginMsUpdatePasswordCommand);
			if (loginSuccess) {
				if (this.oldPasswordList == null) {
					oldPasswordList = new ArrayList<>();
				}
				this.oldPasswordList.add(this.currentPassword);
				this.currentPassword = loginMsUpdatePasswordCommand.getNewPassword();
				this.addPasswordUpdatedEvent(eventList, genericCommand, loginMsUpdatePasswordCommand);
			} else {
				this.addPasswordUpdateFailedEvent(eventList, genericCommand, loginMsUpdatePasswordCommand);
			}

		}

		return eventList;

	}

	private void addUserLoggedInEvent(List<LoginMsGenericEvent> eventList, LoginMsGenericCommand genericCommand,
			LoginMsCheckUserCredentials loginMsCheckUserCredentialsCommand) {
		LoginMsGenericEvent genericEvent = buildGenericEvent(genericCommand);
		genericEvent.setEventName(LoginMsEventType.UserLoggedIn.getEventName());
		LoginMsUserLoggedIn loggedInEvent = new LoginMsUserLoggedIn();
		loggedInEvent.setEmailId(this.emailId);
		loggedInEvent.setFirstName(this.firstName);
		loggedInEvent.setLastName(this.lastName);
		loggedInEvent.setUserName(this.userName);
		genericEvent.setEventData(LoginMSJsonService.convertObjectToJson(loggedInEvent));
		eventList.add(genericEvent);

	}

	private void addUserLoggedInFailedEvent(List<LoginMsGenericEvent> eventList, LoginMsGenericCommand genericCommand,
			LoginMsCheckUserCredentials loginMsCheckUserCredentialsCommand) {
		LoginMsGenericEvent genericEvent = buildGenericEvent(genericCommand);
		genericEvent.setEventName(LoginMsEventType.UserLoggedInFailed.getEventName());
		LoginMsUserLogInFailed loginMsUserLogInFailed = new LoginMsUserLogInFailed();
		loginMsUserLogInFailed.setUserName(loginMsCheckUserCredentialsCommand.getUserName());
		loginMsUserLogInFailed.setNoOfInvalidAttempts(this.noOfInvalidAttempts);
		loginMsUserLogInFailed.setFailureReason("Login Failure");
		genericEvent.setEventData(LoginMSJsonService.convertObjectToJson(loginMsUserLogInFailed));
		eventList.add(genericEvent);

	}

	private void addNewUserRegisteredEvent(List<LoginMsGenericEvent> eventList, LoginMsGenericCommand genericCommand,
			LoginMsRegisterNewUser loginMsRegisterNewUserCommand) {
		LoginMsGenericEvent genericEvent = buildGenericEvent(genericCommand);
		genericEvent.setEventName(LoginMsEventType.NewUserRegistered.getEventName());
		LoginMSNewUserRegsitered loginMSNewUserRegsitered = new LoginMSNewUserRegsitered();
		loginMSNewUserRegsitered.setUserName(this.userName);
		loginMSNewUserRegsitered.setPassword(this.currentPassword);
		loginMSNewUserRegsitered.setFirstName(this.firstName);
		loginMSNewUserRegsitered.setLastName(this.lastName);
		loginMSNewUserRegsitered.setEmailId(this.emailId);
		genericEvent.setEventData(LoginMSJsonService.convertObjectToJson(loginMSNewUserRegsitered));
		eventList.add(genericEvent);
	}

	private void addPasswordUpdatedEvent(List<LoginMsGenericEvent> eventList, LoginMsGenericCommand genericCommand,
			LoginMsUpdatePassword loginMsUpdatePassword) {
		LoginMsGenericEvent genericEvent = buildGenericEvent(genericCommand);
		genericEvent.setEventName(LoginMsEventType.PasswordUpdated.getEventName());
		LoginMsPasswordUpdated loginMsPasswordUpdated = new LoginMsPasswordUpdated();
		loginMsPasswordUpdated.setUserName(this.userName);
		loginMsPasswordUpdated.setNewPassword(this.currentPassword);
		loginMsPasswordUpdated.setOldPassword(this.oldPasswordList.get(this.oldPasswordList.size() - 1));
		genericEvent.setEventData(LoginMSJsonService.convertObjectToJson(loginMsPasswordUpdated));
		eventList.add(genericEvent);
	}

	private void addPasswordUpdateFailedEvent(List<LoginMsGenericEvent> eventList, LoginMsGenericCommand genericCommand,
			LoginMsUpdatePassword loginMsUpdatePasswordCommand) {
		LoginMsGenericEvent genericEvent = buildGenericEvent(genericCommand);
		genericEvent.setEventName(LoginMsEventType.PasswordUpdateFailed.getEventName());
		LoginMsPasswordUpdateFailed loginMsUserLogInFailed = new LoginMsPasswordUpdateFailed();
		loginMsUserLogInFailed.setUserName(loginMsUpdatePasswordCommand.getUserName());
		loginMsUserLogInFailed.setFailedReason(loginMsUpdatePasswordCommand.getPasswordUpdateMessage());
		genericEvent.setEventData(LoginMSJsonService.convertObjectToJson(loginMsUserLogInFailed));
		eventList.add(genericEvent);

	}

	private LoginMsGenericEvent buildGenericEvent(LoginMsGenericCommand genericCommand) {
		LoginMsGenericEvent genericEvent = new LoginMsGenericEvent();
		genericEvent.setAggregateId(this.aggregateId);
		genericEvent.setAggregateType(LoginMsAggregateType.UserAggregate.getAggregateType());
		genericEvent.setOriginAggregateId(genericCommand.getAggregateId());
		genericEvent.setOriginCommandRequestId(genericCommand.getOriginCommandRequestId());
		genericEvent.setOriginCommandName(genericCommand.getCommandName());
		genericEvent.setOriginIssuedTime(genericCommand.getIssuedTime());
		return genericEvent;
	}

	private boolean checkUserCredentials(LoginMsCheckUserCredentials loginMsCheckUserCredentialsCommand) {

		if (this.userName.equals(loginMsCheckUserCredentialsCommand.getUserName())) {
			if (this.currentPassword.equals(loginMsCheckUserCredentialsCommand.getPassword())) {
				return true;
			}
		}

		return false;
	}

	private boolean checkPasswordUpdatePolicy(LoginMsUpdatePassword loginMsUpdatePassword) {

		if (this.userName.equals(loginMsUpdatePassword.getUserName())) {
			if (this.currentPassword.equals(loginMsUpdatePassword.getOldPassword())) {
				if (oldPasswordList != null) {
					for (String string : oldPasswordList) {
						if (string.equals(loginMsUpdatePassword.getNewPassword())) {
							loginMsUpdatePassword.setPasswordUpdateMessage("Password Already Available");
							return false;
						}
					}
				}

				return true;
			}
			loginMsUpdatePassword.setPasswordUpdateMessage("Old Password Not Matched");
			return false;
		}
		loginMsUpdatePassword.setPasswordUpdateMessage("Invalid User");
		return false;
	}

	private void incrementInvalidAttempts() {
		this.noOfInvalidAttempts++;
	}

	private void resetInvalidAttempts() {
		this.noOfInvalidAttempts = 0;
	}

}
