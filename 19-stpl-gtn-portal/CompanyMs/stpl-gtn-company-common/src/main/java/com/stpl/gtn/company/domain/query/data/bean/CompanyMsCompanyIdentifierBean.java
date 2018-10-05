package com.stpl.gtn.company.domain.query.data.bean;

public class CompanyMsCompanyIdentifierBean {

	public CompanyMsCompanyIdentifierBean() {
		super();
	}

	private String aggregateId;
	private String companyQualifierName;
	private String companyIdentifier;
	private String identifierStatus;
	private String startDate;
	private String endDate;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;
	private String effectiveDates;
	private String notes;

	public String getCompanyQualifierName() {
		return companyQualifierName;
	}

	public void setCompanyQualifierName(String companyQualifierName) {
		this.companyQualifierName = companyQualifierName;
	}

	public String getCompanyIdentifier() {
		return companyIdentifier;
	}

	public void setCompanyIdentifier(String companyIdentifier) {
		this.companyIdentifier = companyIdentifier;
	}

	public String getIdentifierStatus() {
		return identifierStatus;
	}

	public void setIdentifierStatus(String identifierStatus) {
		this.identifierStatus = identifierStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getEffectiveDates() {
		return effectiveDates;
	}

	public void setEffectiveDates(String effectiveDates) {
		this.effectiveDates = effectiveDates;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

}
