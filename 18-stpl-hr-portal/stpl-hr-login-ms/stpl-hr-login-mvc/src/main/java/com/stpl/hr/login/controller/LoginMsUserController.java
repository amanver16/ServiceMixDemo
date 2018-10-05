package com.stpl.hr.login.controller;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.hr.login.bus.consumer.LoginMsEventTracker;
import com.stpl.hr.login.bus.gateway.LoginMsQueryModelGateway;
import com.stpl.hr.login.bus.producer.LoginMsCommandProducer;
import com.stpl.hr.login.domain.command.LoginMsCheckUserCredentials;
import com.stpl.hr.login.domain.command.LoginMsCommandType;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.command.LoginMsUpdatePassword;
import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsPasswordUpdateFailed;
import com.stpl.hr.login.domain.event.LoginMsPasswordUpdated;
import com.stpl.hr.login.domain.event.LoginMsUserLogInFailed;
import com.stpl.hr.login.domain.event.LoginMsUserLoggedIn;
import com.stpl.hr.login.domain.service.LoginMSJsonService;
import com.stpl.hr.login.mvc.request.LoginMsRequest;
import com.stpl.hr.login.mvc.request.LoginMsUpdatePasswordRequest;
import com.stpl.hr.login.mvc.response.LoginMsResponse;
import com.stpl.hr.login.mvc.response.LoginMsUpdatePasswordResponse;

@Path("/loginMs")
public class LoginMsUserController {

	private LoginMsCommandProducer loginMsCommandProducer;

	private LoginMsEventTracker loginMsEventTracker;

	private LoginMsQueryModelGateway loginMsQueryModelGateway;

	private int timeOutSeconds = 30;

	public LoginMsCommandProducer getLoginMsCommandProducer() {
		return loginMsCommandProducer;
	}

	public void setLoginMsCommandProducer(LoginMsCommandProducer loginMsCommandProducer) {
		this.loginMsCommandProducer = loginMsCommandProducer;
	}

	public LoginMsEventTracker getLoginMsEventTracker() {
		return loginMsEventTracker;
	}

	public void setLoginMsEventTracker(LoginMsEventTracker loginMsEventTracker) {
		this.loginMsEventTracker = loginMsEventTracker;
	}

	public LoginMsQueryModelGateway getLoginMsQueryModelGateway() {
		return loginMsQueryModelGateway;
	}

	public void setLoginMsQueryModelGateway(LoginMsQueryModelGateway loginMsQueryModelGateway) {
		this.loginMsQueryModelGateway = loginMsQueryModelGateway;
	}

	@POST
	@Path("/checkUserCredentials")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.APPLICATION_JSON)
	public String checkUserCredentials(String userLoginDetails) {

		String methodName = "checkUserCredentials";

		LoginMsResponse loginMsResponse = new LoginMsResponse();
		loginMsResponse.setStatus("Exception");
		loginMsResponse.setFailureReason("Event not received");

		try {
			LoginMsRequest loginMsRequest = (LoginMsRequest) LoginMSJsonService
					.convertJsonToObject(LoginMsRequest.class, userLoginDetails);

			String aggregateId = loginMsQueryModelGateway.getAggregateIdByUserName(loginMsRequest.getUserName());
			if (aggregateId == null) {
				loginMsResponse.setStatus("Failure");
				loginMsResponse.setFailureReason("Username or password is wrong.");
				String convertedResponse = LoginMSJsonService.convertObjectToJson(loginMsResponse);
				System.out
						.println("MVC Layer: Without triggering command and event Response to UI " + convertedResponse);
				return convertedResponse;
			}

			LoginMsGenericCommand genericCommand = buildUserCredentialsCommand(loginMsRequest, aggregateId);

			loginMsCommandProducer.initiateCommand(genericCommand);

			loginMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			LoginMsGenericEvent eventResponse = loginMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), timeOutSeconds);

			System.out.println(
					"LoginMs : MVC Layer: Received response " + LoginMSJsonService.convertObjectToJson(eventResponse));

			if (eventResponse != null) {

				buildLoginMsResponse(loginMsResponse, eventResponse);

			}

		} catch (Exception e) {
			e.printStackTrace();
			loginMsResponse.setFailureReason("Http Exception while processing " + methodName);
		}

		String convertedResponse = LoginMSJsonService.convertObjectToJson(loginMsResponse);
		System.out.println("LoginMs : MVC Layer: Response to UI " + convertedResponse);
		return convertedResponse;

	}

	private void buildLoginMsResponse(LoginMsResponse loginMsResponse, LoginMsGenericEvent eventResponse)
			throws IOException {

		loginMsResponse.setAggregateId(eventResponse.getAggregateId());

		if (eventResponse.getEventName().equals(LoginMsEventType.UserLoggedIn.getEventName())) {
			loginMsResponse.setStatus("Success");
			loginMsResponse.setFailureReason("");
			LoginMsUserLoggedIn loggedInEventData = (LoginMsUserLoggedIn) LoginMSJsonService
					.convertJsonToObject(LoginMsUserLoggedIn.class, eventResponse.getEventData());
			loginMsResponse.setUserName(loggedInEventData.getUserName());
			loginMsResponse.setFirstName(loggedInEventData.getFirstName());
			loginMsResponse.setLastName(loggedInEventData.getLastName());
			loginMsResponse.setEmailId(loggedInEventData.getEmailId());

		}
		if (eventResponse.getEventName().equals(LoginMsEventType.UserLoggedInFailed.getEventName())) {
			loginMsResponse.setStatus("Failure");
			LoginMsUserLogInFailed loginFailedEventData = (LoginMsUserLogInFailed) LoginMSJsonService
					.convertJsonToObject(LoginMsUserLogInFailed.class, eventResponse.getEventData());

			loginMsResponse.setFailureReason(loginFailedEventData.getFailureReason());

		}
	}

	private LoginMsGenericCommand buildUserCredentialsCommand(LoginMsRequest loginMsRequest, String aggregateId) {
		LoginMsCheckUserCredentials userCredentialsCommand = new LoginMsCheckUserCredentials();
		userCredentialsCommand.setUserName(loginMsRequest.getUserName());
		userCredentialsCommand.setPassword(loginMsRequest.getPassword());
		String commandData = LoginMSJsonService.convertObjectToJson(userCredentialsCommand);

		LoginMsGenericCommand genericCommand = new LoginMsGenericCommand();
		genericCommand.setAggregateId(aggregateId);
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setCommandName(LoginMsCommandType.CheckUserCredentials.getCommandName());
		return genericCommand;
	}

	@POST
	@Path("/updatePassword")
	@Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.APPLICATION_JSON)
	public String updatePassword(String inputJson) {
		String methodName = "updatePassword";

		LoginMsUpdatePasswordResponse httpResponse = new LoginMsUpdatePasswordResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");

		try {
			LoginMsUpdatePasswordRequest updatePasswordRequest = (LoginMsUpdatePasswordRequest) LoginMSJsonService
					.convertJsonToObject(LoginMsUpdatePasswordRequest.class, inputJson);

			String aggregateId = updatePasswordRequest.getAggregateId();

			LoginMsGenericCommand genericCommand = buildUpdatePasswordCommand(updatePasswordRequest, aggregateId);

			loginMsCommandProducer.initiateCommand(genericCommand);
			loginMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			LoginMsGenericEvent eventResponse = loginMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), timeOutSeconds);

			System.out.println("LoginMs : MVC Layer: Received response " + eventResponse);

			if (eventResponse != null) {

				buildLoginMsUpdatePasswordResponse(httpResponse, eventResponse);

			}

		} catch (Exception e) {
			e.printStackTrace();
			httpResponse.setFailureReason("Http Exception while processing " + methodName);
		}

		String convertedResponse = LoginMSJsonService.convertObjectToJson(httpResponse);
		return convertedResponse;

	}

	private void buildLoginMsUpdatePasswordResponse(LoginMsUpdatePasswordResponse httpResponse,
			LoginMsGenericEvent eventResponse) throws IOException {
		httpResponse.setAggregateId(eventResponse.getAggregateId());
		httpResponse.setStatus("Failure");
		httpResponse.setFailureReason("Update Password Failure");
		if (eventResponse.getEventName().equals(LoginMsEventType.PasswordUpdated.getEventName())) {
			LoginMsPasswordUpdated passwordUpdatedEventData = (LoginMsPasswordUpdated) LoginMSJsonService
					.convertJsonToObject(LoginMsPasswordUpdated.class, eventResponse.getEventData());
			httpResponse.setUserName(passwordUpdatedEventData.getUserName());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(LoginMsEventType.PasswordUpdateFailed.getEventName())) {
			LoginMsPasswordUpdateFailed passwordUpdatedEventData = (LoginMsPasswordUpdateFailed) LoginMSJsonService
					.convertJsonToObject(LoginMsPasswordUpdateFailed.class, eventResponse.getEventData());
			httpResponse.setUserName(passwordUpdatedEventData.getUserName());
			httpResponse.setFailureReason(passwordUpdatedEventData.getFailedReason());
		}
		httpResponse.setAggregateId(httpResponse.getAggregateId());
	}

	private LoginMsGenericCommand buildUpdatePasswordCommand(LoginMsUpdatePasswordRequest updatePasswordRequest,
			String aggregateId) {
		LoginMsUpdatePassword updatePasswordCommand = new LoginMsUpdatePassword();

		updatePasswordCommand.setUserName(updatePasswordRequest.getUserName());
		updatePasswordCommand.setOldPassword(updatePasswordRequest.getOldPassword());
		updatePasswordCommand.setNewPassword(updatePasswordRequest.getNewPassword());

		String commandData = LoginMSJsonService.convertObjectToJson(updatePasswordCommand);

		LoginMsGenericCommand genericCommand = new LoginMsGenericCommand();
		genericCommand.setAggregateId(aggregateId);
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setCommandName(LoginMsCommandType.UpdatePassword.getCommandName());
		return genericCommand;
	}

	@POST
	@Path("/test")
	@Produces("application/json")
	public String test(String userLoginDetails) {
		ObjectMapper mapper = new ObjectMapper();
		LoginMsResponse loginMsResponse = new LoginMsResponse();
		loginMsResponse.setStatus("Failure");
		try {
			System.out.println("LoginMs : MVC Layer: Calling web Service===========================");
			LoginMsRequest loginMsRequest = mapper.readValue(userLoginDetails, LoginMsRequest.class);
			if (("admin".equals(loginMsRequest.getUserName()) && ("admin".equals(loginMsRequest.getPassword())))) {
				loginMsResponse.setUserName(loginMsRequest.getUserName());
				loginMsResponse.setFirstName("Stpl");
				loginMsResponse.setLastName("Admin");
				loginMsResponse.setStatus("Success");
				loginMsResponse.setAggregateId(UUID.randomUUID().toString());
			}
			return mapper.writeValueAsString(loginMsResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
