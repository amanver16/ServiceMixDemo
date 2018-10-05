package com.stpl.gtn.company.domain.command;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;

public class CompanyMsAddCompany {

	private CompanyMsCompanyBean companyMasterBean;

	public CompanyMsAddCompany() {
		super();
	}

	public CompanyMsCompanyBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMsCompanyBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

}
