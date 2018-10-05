package com.stpl.gtn.company.domain.event;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;

public class CompanyMsCompanyAdded {

	private CompanyMsCompanyBean companyMasterBean;

	public CompanyMsCompanyAdded() {
		super();
	}

	public CompanyMsCompanyBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMsCompanyBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

}
