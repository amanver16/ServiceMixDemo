package com.stpl.servicemix.associate.writedb.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.associate.ui.bean.AssociateBean;

public class AssociateWriteDbService {

	AssociateServiceListImpl listWriteDB = new AssociateServiceListImpl();

	public void insertInWriteDbService(String input) {
		System.out.println("Input to Write db : " + input);
		// AssociateBean bean = (AssociateBean) input;
		ObjectMapper mapper = new ObjectMapper();
		try {
			AssociateBean associate = mapper.readValue(input, AssociateBean.class);
			System.out.println(associate);
			listWriteDB.addAssociate(associate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteAssociate(String input) {
		listWriteDB.deleteAssociate(Integer.parseInt(input));
	}

	public void deleteAllAssociate(String input) {
		listWriteDB.deleteAllAssociate();
	}

	public void updateAssociate(String input) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			AssociateBean associate = mapper.readValue(input, AssociateBean.class);
			System.out.println(associate);
			listWriteDB.updateAssociate(associate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
