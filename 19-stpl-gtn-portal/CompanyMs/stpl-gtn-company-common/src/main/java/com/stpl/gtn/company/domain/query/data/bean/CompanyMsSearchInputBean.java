package com.stpl.gtn.company.domain.query.data.bean;

import java.util.List;

public class CompanyMsSearchInputBean {

	private List<CompanyMsSearchCriteria> searchCriteriaList;
	private int start;
	private int offset;

	public List<CompanyMsSearchCriteria> getSearchCriteriaList() {
		return searchCriteriaList;
	}

	public void setSearchCriteriaList(List<CompanyMsSearchCriteria> searchCriteriaList) {
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
