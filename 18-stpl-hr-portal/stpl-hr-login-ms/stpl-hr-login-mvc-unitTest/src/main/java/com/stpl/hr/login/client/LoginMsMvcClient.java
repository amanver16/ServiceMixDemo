package com.stpl.hr.login.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.cxf.helpers.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsPasswordUpdated;
import com.stpl.hr.login.domain.event.LoginMsUserLoggedIn;
import com.stpl.hr.login.domain.service.LoginMSJsonService;
import com.stpl.hr.login.mvc.request.LoginMsRequest;
import com.stpl.hr.login.mvc.request.LoginMsUpdatePasswordRequest;

public class LoginMsMvcClient {
	private static final String LOGINMS_SERVICE_URL = "http://localhost:8989/OnboardMVC/OnboardMs";

	public static void main(String args[]) throws Exception {

		LoginMsMvcClient mvcClient = new LoginMsMvcClient();
		mvcClient.updatePassword();
		// mvcClient.updatePassword();

	}

	public void checkUserCredentials() throws Exception {
		LoginMsRequest httpLoginRequest = new LoginMsRequest();
		httpLoginRequest.setUserName("harlin.mani");
		httpLoginRequest.setPassword("welcome");
		this.generateEventForLoginSuccess();
		this.postRestUrl(httpLoginRequest, "/checkUserCredentials");
	}

	public void updatePassword() throws Exception {
		LoginMsUpdatePasswordRequest request = new LoginMsUpdatePasswordRequest();
		request.setUserName("harlin.mani");
		request.setOldPassword("new");
		request.setNewPassword("new");

		this.generateEventForUpdatePassword();
		this.postRestUrl(request, "/getAllAssociate");
	}

	private void generateEventForUpdatePassword() {
		String eventJson = "";

		LoginMsGenericEvent event = new LoginMsGenericEvent();
		event.setAggregateId("sdsf");
		event.setOriginCommandRequestId("ReplaceRequestID");
		event.setEventName("LoginMsPasswordUpdated");

		LoginMsPasswordUpdated passwordUpdatedEvent = new LoginMsPasswordUpdated();

		passwordUpdatedEvent.setStatus("Success");
		passwordUpdatedEvent.setUserName("rajadurai.s");
		passwordUpdatedEvent.setOldPassword("old");
		passwordUpdatedEvent.setNewPassword("new");

		event.setEventData(LoginMSJsonService.convertObjectToJson(passwordUpdatedEvent));

		eventJson = LoginMSJsonService.convertObjectToJson(event);

		System.out.println(eventJson);

	}

	public void generateEventForLoginSuccess() {

		String eventJson = "";

		LoginMsGenericEvent event = new LoginMsGenericEvent();
		event.setAggregateId("e5b54d62-adc7-424e-aa29-aa84a9eb2a63");
		event.setOriginCommandRequestId("ReplaceRequestID");
		event.setEventName("UserLoggedIn");

		LoginMsUserLoggedIn loggedInEvent = new LoginMsUserLoggedIn();
		loggedInEvent.setFirstName("rajadurai");
		loggedInEvent.setLastName("subramanian");
		loggedInEvent.setEmailId("rajadurai.s@sysbiz.com");
		loggedInEvent.setUserName("rajadurai.s");
		event.setEventData(LoginMSJsonService.convertObjectToJson(loggedInEvent));

		eventJson = LoginMSJsonService.convertObjectToJson(event);

		System.out.println(eventJson);

	}

	public void postRestUrl(Object request, String wsMethod) throws Exception {
		String url = LOGINMS_SERVICE_URL + wsMethod;
		HttpURLConnection connection = connect(url);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");

		System.out.println("Posting to url " + url);
		writeObjectToOutput(request, connection.getOutputStream());

		System.out.println("Waiting response from url " + url);
		InputStream stream = connection.getResponseCode() / 100 == 2 ? connection.getInputStream()
				: connection.getErrorStream();
		System.out.println("Response : " + IOUtils.toString(stream) + " End");
	}

	private HttpURLConnection connect(String url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		return connection;
	}

	private void writeObjectToOutput(Object value, OutputStream output) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(output, value);
	}

}
