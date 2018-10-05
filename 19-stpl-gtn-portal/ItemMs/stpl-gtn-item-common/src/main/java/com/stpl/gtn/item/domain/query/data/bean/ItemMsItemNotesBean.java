package com.stpl.gtn.item.domain.query.data.bean;

import java.util.List;

public class ItemMsItemNotesBean {

	private String notes;

	private List<ItemMsDocumentBean> documentList;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<ItemMsDocumentBean> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<ItemMsDocumentBean> documentList) {
		this.documentList = documentList;
	}

}
