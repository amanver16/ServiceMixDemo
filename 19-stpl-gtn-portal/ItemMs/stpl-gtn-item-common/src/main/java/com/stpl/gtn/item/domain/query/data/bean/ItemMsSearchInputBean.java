package com.stpl.gtn.item.domain.query.data.bean;

import java.util.List;

public class ItemMsSearchInputBean {

	private List<ItemMsSearchCriteria> searchCriteriaList;
	private int start;
	private int offset;

	public List<ItemMsSearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<ItemMsSearchCriteria> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
