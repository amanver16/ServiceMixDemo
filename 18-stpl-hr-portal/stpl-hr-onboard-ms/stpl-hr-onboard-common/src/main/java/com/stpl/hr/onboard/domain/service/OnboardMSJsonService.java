package com.stpl.hr.onboard.domain.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.hr.onboard.domain.command.OnboardMsGenericCommand;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;

public class OnboardMSJsonService {

	public static String convertObjectToJson(Object value) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			System.out.println("Error in converting object to json " + value);
			e.printStackTrace();
		}

		return "";
	}

	public static Object convertJsonToObject(Class<?> classObj, String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, classObj);
	}

	public static OnboardMsGenericCommand buildCommandFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, OnboardMsGenericCommand.class);
	}

	public static OnboardMsGenericEvent buildEventFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, OnboardMsGenericEvent.class);
	}

}
