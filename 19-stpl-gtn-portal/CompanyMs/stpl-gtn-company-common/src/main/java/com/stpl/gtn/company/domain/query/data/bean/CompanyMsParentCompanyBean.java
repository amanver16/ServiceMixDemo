package com.stpl.gtn.company.domain.query.data.bean;

public class CompanyMsParentCompanyBean {

	public CompanyMsParentCompanyBean() {
		super();
	}

	private String parentCompanyNo;
	private String parentCompanyName;
	private String parentCompanyStartDate;
	private String parentCompanyEndDate;
	private String createdBy;
	private String createdDate;
	private String modifiedBy;
	private String modifiedDate;

	public String getParentCompanyNo() {
		return parentCompanyNo;
	}

	public void setParentCompanyNo(String parentCompanyNo) {
		this.parentCompanyNo = parentCompanyNo;
	}

	public String getParentCompanyName() {
		return parentCompanyName;
	}

	public void setParentCompanyName(String parentCompanyName) {
		this.parentCompanyName = parentCompanyName;
	}

	public String getParentCompanyStartDate() {
		return parentCompanyStartDate;
	}

	public void setParentCompanyStartDate(String parentCompanyStartDate) {
		this.parentCompanyStartDate = parentCompanyStartDate;
	}

	public String getParentCompanyEndDate() {
		return parentCompanyEndDate;
	}

	public void setParentCompanyEndDate(String parentCompanyEndDate) {
		this.parentCompanyEndDate = parentCompanyEndDate;
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

}
