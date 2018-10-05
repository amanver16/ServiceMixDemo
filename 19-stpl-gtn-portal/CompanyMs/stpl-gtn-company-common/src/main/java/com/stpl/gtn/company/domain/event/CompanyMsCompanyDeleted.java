package com.stpl.gtn.company.domain.event;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;

public class CompanyMsCompanyDeleted {

	private CompanyMsCompanyBean companyMasterBean;

	public CompanyMsCompanyDeleted() {
		super();
	}

	public CompanyMsCompanyBean getCompanyMasterBean() {
		return companyMasterBean;
	}

	public void setCompanyMasterBean(CompanyMsCompanyBean companyMasterBean) {
		this.companyMasterBean = companyMasterBean;
	}

}
