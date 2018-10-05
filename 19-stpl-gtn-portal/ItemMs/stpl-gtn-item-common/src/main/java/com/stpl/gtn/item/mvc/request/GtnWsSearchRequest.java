package com.stpl.gtn.item.mvc.request;

import java.util.List;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsSearchCriteria;

public class GtnWsSearchRequest {

	public GtnWsSearchRequest() {
		super();
	}

	private boolean isCount;
	private List<ItemMsSearchCriteria> searchCriteriaList;
	private int tableRecordStart;
	private int tableRecordOffset;

	public boolean isCount() {
		return isCount;
	}

	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}

	public List<ItemMsSearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<ItemMsSearchCriteria> searchCriteriaList) {
		this.searchCriteriaList = searchCriteriaList;
	}

	public int getTableRecordStart() {
		return tableRecordStart;
	}

	public void setTableRecordStart(int tableRecordStart) {
		this.tableRecordStart = tableRecordStart;
	}

	public int getTableRecordOffset() {
		return tableRecordOffset;
	}

	public void setTableRecordOffset(int tableRecordOffset) {
		this.tableRecordOffset = tableRecordOffset;
	}

}
