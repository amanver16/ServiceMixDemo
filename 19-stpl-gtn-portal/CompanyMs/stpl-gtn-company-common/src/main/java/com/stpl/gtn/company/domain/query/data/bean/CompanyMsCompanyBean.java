package com.stpl.gtn.company.domain.query.data.bean;

import java.util.List;

public class CompanyMsCompanyBean {

	private String aggregateId;
	private CompanyMsCompanyInformationBean companyInformationBean;
	private CompanyMsAddressBean addressBean;
	private List<CompanyMsCompanyIdentifierBean> companyIdentifierBean;
	private List<CompanyMsCompanyTradeClassBean> companyTradeClassBean;
	private List<CompanyMsParentCompanyBean> parentCompanyBean;
	private CompanyMsNotesBean companyMsNotesBean;

	public CompanyMsCompanyBean() {
		super();
	}

	public void setCompanyMasterBeans(CompanyMsCompanyInformationBean companyInformationBean,
			CompanyMsAddressBean addressBean, List<CompanyMsCompanyIdentifierBean> companyIdentifierBean,
			List<CompanyMsCompanyTradeClassBean> companyTradeClassBean,
			List<CompanyMsParentCompanyBean> parentCompanyBean, CompanyMsNotesBean companyMsNotesBean) {

		this.companyInformationBean = companyInformationBean;
		this.addressBean = addressBean;
		this.setCompanyIdentifierBean(companyIdentifierBean);
		this.setCompanyTradeClassBean(companyTradeClassBean);
		this.setParentCompanyBean(parentCompanyBean);
		this.setCompanyMsNotesBean(companyMsNotesBean);

	}

	public CompanyMsCompanyInformationBean getCompanyInformationBean() {
		return companyInformationBean;
	}

	public void setCompanyInformationBean(CompanyMsCompanyInformationBean companyInformationBean) {
		this.companyInformationBean = companyInformationBean;
	}

	public CompanyMsAddressBean getAddressBean() {
		return addressBean;
	}

	public void setAddressBean(CompanyMsAddressBean addressBean) {
		this.addressBean = addressBean;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public List<CompanyMsCompanyIdentifierBean> getCompanyIdentifierBean() {
		return companyIdentifierBean;
	}

	public void setCompanyIdentifierBean(List<CompanyMsCompanyIdentifierBean> companyIdentifierBean) {
		this.companyIdentifierBean = companyIdentifierBean;
	}

	public List<CompanyMsCompanyTradeClassBean> getCompanyTradeClassBean() {
		return companyTradeClassBean;
	}

	public void setCompanyTradeClassBean(List<CompanyMsCompanyTradeClassBean> companyTradeClassBean) {
		this.companyTradeClassBean = companyTradeClassBean;
	}

	public CompanyMsNotesBean getCompanyMsNotesBean() {
		return companyMsNotesBean;
	}

	public void setCompanyMsNotesBean(CompanyMsNotesBean companyMsNotesBean) {
		this.companyMsNotesBean = companyMsNotesBean;
	}

	public List<CompanyMsParentCompanyBean> getParentCompanyBean() {
		return parentCompanyBean;
	}

	public void setParentCompanyBean(List<CompanyMsParentCompanyBean> parentCompanyBean) {
		this.parentCompanyBean = parentCompanyBean;
	}

}
