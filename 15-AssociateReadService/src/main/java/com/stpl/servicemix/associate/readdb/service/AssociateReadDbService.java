package com.stpl.servicemix.associate.readdb.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.associate.ui.bean.AssociateBean;

public class AssociateReadDbService {

	AssociateServiceListImpl listReadDB = new AssociateServiceListImpl();

	public void insertInReadDbService(String input) {
		System.out.println("Input to read db : " + input);
		// AssociateBean bean = (AssociateBean) input;
		ObjectMapper mapper = new ObjectMapper();
		try {
			AssociateBean associate = mapper.readValue(input, AssociateBean.class);
			System.out.println(associate);
			listReadDB.addAssociate(associate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteAssociate(String input) {
		listReadDB.deleteAssociate(Integer.parseInt(input));
	}

	public void deleteAllAssociate(String input) {
		listReadDB.deleteAllAssociate();
	}

	public void updateAssociate(String input) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			AssociateBean associate = mapper.readValue(input, AssociateBean.class);
			System.out.println(associate);
			listReadDB.updateAssociate(associate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readAllAssociate() {
		System.out.println("Control in read db read all");
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(listReadDB.getAssociateList());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
