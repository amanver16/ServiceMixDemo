package com.stpl.gtn.company.domain.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.gtn.company.domain.command.CompanyMsGenericCommand;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;

public class CompanyMsJsonService {
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

	public static CompanyMsGenericCommand buildCommandFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, CompanyMsGenericCommand.class);
	}

	public static CompanyMsGenericEvent buildEventFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, CompanyMsGenericEvent.class);
	}

}
