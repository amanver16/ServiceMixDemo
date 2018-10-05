package com.stpl.gtn.company.mvc.request;

public class GtnFrameworkWebserviceRequest {

	public GtnFrameworkWebserviceRequest() {
		super();
	}

	private GtnWsSearchRequest gtnWsSearchRequest;
	private GtnWsCompanyDeleteRequest wsCompanyDeleteRequest;
	private CompanyMsCompanyInventoryRequest companyInventoryRequest;

	public GtnWsSearchRequest getGtnWsSearchRequest() {
		return gtnWsSearchRequest;
	}

	public void setGtnWsSearchRequest(GtnWsSearchRequest gtnWsSearchRequest) {
		this.gtnWsSearchRequest = gtnWsSearchRequest;
	}

	public GtnWsCompanyDeleteRequest getWsCompanyDeleteRequest() {
		return wsCompanyDeleteRequest;
	}

	public void setWsCompanyDeleteRequest(GtnWsCompanyDeleteRequest wsCompanyDeleteRequest) {
		this.wsCompanyDeleteRequest = wsCompanyDeleteRequest;
	}

	public CompanyMsCompanyInventoryRequest getCompanyInventoryRequest() {
		return companyInventoryRequest;
	}

	public void setCompanyInventoryRequest(CompanyMsCompanyInventoryRequest companyInventoryRequest) {
		this.companyInventoryRequest = companyInventoryRequest;
	}

}
