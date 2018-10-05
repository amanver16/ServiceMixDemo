package com.stpl.gtn.company.domain.query.data.bean;

public class CompanyMsCompanyTradeClassBean {

	public CompanyMsCompanyTradeClassBean() {
		super();
	}

	private String aggregateId;
	private String tradeClass;
	private String tradeClassStartDate;
	private String tradeClassEndDate;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;

	public String getTradeClass() {
		return tradeClass;
	}

	public void setTradeClass(String tradeClass) {
		this.tradeClass = tradeClass;
	}

	public String getTradeClassStartDate() {
		return tradeClassStartDate;
	}

	public void setTradeClassStartDate(String tradeClassStartDate) {
		this.tradeClassStartDate = tradeClassStartDate;
	}

	public String getTradeClassEndDate() {
		return tradeClassEndDate;
	}

	public void setTradeClassEndDate(String tradeClassEndDate) {
		this.tradeClassEndDate = tradeClassEndDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

}
