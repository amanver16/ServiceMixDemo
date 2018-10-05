package com.stpl.gtn.company.domain.query.data.bean;

import java.util.List;

public class CompanyMsNotesBean {

	public CompanyMsNotesBean() {
		super();
	}

	private String notes;

	private List<CompanyMsDocumentBean> documentList;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<CompanyMsDocumentBean> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CompanyMsDocumentBean> documentList) {
		this.documentList = documentList;
	}

}
