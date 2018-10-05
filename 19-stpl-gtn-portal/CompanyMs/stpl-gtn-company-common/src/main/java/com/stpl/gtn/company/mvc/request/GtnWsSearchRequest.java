package com.stpl.gtn.company.mvc.request;

import java.util.List;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchCriteria;

public class GtnWsSearchRequest {

	public GtnWsSearchRequest() {
		super();
	}

	private boolean isCount;
	private List<CompanyMsSearchCriteria> searchCriteriaList;
	private int tableRecordStart;
	private int tableRecordOffset;

	public boolean isCount() {
		return isCount;
	}

	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}

	public List<CompanyMsSearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<CompanyMsSearchCriteria> searchCriteriaList) {
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
