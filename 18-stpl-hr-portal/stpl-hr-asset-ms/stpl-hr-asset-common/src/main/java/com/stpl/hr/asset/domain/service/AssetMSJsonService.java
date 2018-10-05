package com.stpl.hr.asset.domain.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;

public class AssetMSJsonService {

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

	public static AssetMsGenericCommand buildCommandFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, AssetMsGenericCommand.class);
	}

	public static AssetMsGenericEvent buildEventFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, AssetMsGenericEvent.class);
	}

}
