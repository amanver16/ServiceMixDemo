package com.stpl.gtn.item.domain.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.gtn.item.domain.command.ItemMsGenericCommand;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;

public class ItemMsJsonService {
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

	public static ItemMsGenericCommand buildCommandFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, ItemMsGenericCommand.class);
	}

	public static ItemMsGenericEvent buildEventFromJson(String jsonInput) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonInput, ItemMsGenericEvent.class);
	}

}
