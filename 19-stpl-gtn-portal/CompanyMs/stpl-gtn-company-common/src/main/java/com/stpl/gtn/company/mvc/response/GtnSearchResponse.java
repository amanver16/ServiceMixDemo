package com.stpl.gtn.company.mvc.response;

import java.util.List;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;

public class GtnSearchResponse {

	public GtnSearchResponse() {
		super();
	}

	private List<CompanyMsSearchBean> companyBeanList;

	public List<CompanyMsSearchBean> getCompanyBeanList() {
		return companyBeanList;
	}

	public void setCompanyBeanList(List<CompanyMsSearchBean> companyBeanList) {
		this.companyBeanList = companyBeanList;
	}

}
