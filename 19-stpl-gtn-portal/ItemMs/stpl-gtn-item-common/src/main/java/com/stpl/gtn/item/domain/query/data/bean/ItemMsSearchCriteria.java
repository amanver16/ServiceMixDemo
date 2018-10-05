package com.stpl.gtn.item.domain.query.data.bean;

public class ItemMsSearchCriteria {
	private String fieldId;
	private Object filterValue1;
	private Object filterValue2;
	private Object filterValue3;
	private String expression;
	private boolean filter = false;

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public Object getFilterValue1() {
		return filterValue1;
	}

	public void setFilterValue1(Object filterValue1) {
		this.filterValue1 = filterValue1;
	}

	public Object getFilterValue2() {
		return filterValue2;
	}

	public void setFilterValue2(Object filterValue2) {
		this.filterValue2 = filterValue2;
	}

	public Object getFilterValue3() {
		return filterValue3;
	}

	public void setFilterValue3(Object filterValue3) {
		this.filterValue3 = filterValue3;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public boolean isFilter() {
		return filter;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

}
