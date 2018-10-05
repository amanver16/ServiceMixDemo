package com.stpl.gtn.company.mvc.request;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;

public class CompanyMsCompanyInventoryRequest {

	public CompanyMsCompanyInventoryRequest() {
		super();
	}

	private CompanyMsCompanyBean companyBean;

	public CompanyMsCompanyBean getCompanyBean() {
		return companyBean;
	}

	public void setCompanyBean(CompanyMsCompanyBean companyBean) {
		this.companyBean = companyBean;
	}

}
