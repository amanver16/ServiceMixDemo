package com.stpl.gtn.company.mvc.response;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;

public class CompanyMsCompanyInventoryResponse {

	public CompanyMsCompanyInventoryResponse() {
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
