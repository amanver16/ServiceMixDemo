package com.stpl.hr.login.domain.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;

public class LoginMSJsonService {

	public static String convertObjectToJson(Object value) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			System.out.println("LoginMs: Common: Error in converting object to json " + value);
			e.printStackTrace();
		}

		return "";
	}

	public static Object convertJsonToObject(Class<?> classObj, String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, classObj);
	}

	public static LoginMsGenericCommand buildCommandFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, LoginMsGenericCommand.class);
	}

	public static LoginMsGenericEvent buildEventFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, LoginMsGenericEvent.class);
	}

}
