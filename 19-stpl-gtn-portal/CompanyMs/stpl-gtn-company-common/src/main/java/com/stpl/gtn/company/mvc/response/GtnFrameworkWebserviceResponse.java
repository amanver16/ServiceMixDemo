package com.stpl.gtn.company.mvc.response;

import java.util.List;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;

public class GtnFrameworkWebserviceResponse {

	public GtnFrameworkWebserviceResponse() {
		super();
	}

	private String status;
	private String failureReason;
	private boolean sucess;
	private Exception gtnGeneralException;
	private GtnSearchResponse gtnSearchResponse;
	private CompanyMsCompanyInventoryResponse companyInventoryResponse;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public GtnSearchResponse getGtnSearchResponse() {
		return gtnSearchResponse;
	}

	public void setGtnSearchResponse(GtnSearchResponse gtnSearchResponse) {
		this.gtnSearchResponse = gtnSearchResponse;
	}

	public void addCompanyBeanToSearchResponse(List<CompanyMsSearchBean> companyBeanList) {
		gtnSearchResponse = new GtnSearchResponse();
		gtnSearchResponse.setCompanyBeanList(companyBeanList);

	}

	public CompanyMsCompanyInventoryResponse getCompanyInventoryResponse() {
		return companyInventoryResponse;
	}

	public void setCompanyInventoryResponse(CompanyMsCompanyInventoryResponse companyInventoryResponse) {
		this.companyInventoryResponse = companyInventoryResponse;
	}

	public Exception getGtnGeneralException() {
		return gtnGeneralException;
	}

	public void setGtnGeneralException(Exception gtnGeneralException) {
		this.gtnGeneralException = gtnGeneralException;
	}

}
