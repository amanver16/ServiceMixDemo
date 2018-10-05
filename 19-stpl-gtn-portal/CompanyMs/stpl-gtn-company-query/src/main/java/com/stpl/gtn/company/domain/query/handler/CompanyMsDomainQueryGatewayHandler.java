package com.stpl.gtn.company.domain.query.handler;

import java.io.IOException;
import java.util.List;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyIdentifierBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyTradeClassBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchInputBean;
import com.stpl.gtn.company.domain.query.mongo.service.CompanyMsQueryMongoService;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsDomainQueryGatewayHandler {

	private CompanyMsQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(CompanyMsQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public long getCompanyCount(String searchInput) throws IOException {
		CompanyMsSearchInputBean convertedSearchInput = (CompanyMsSearchInputBean) CompanyMsJsonService
				.convertJsonToObject(CompanyMsSearchInputBean.class, searchInput);
		return domainQueryDbSevice.queryCompanyCount(convertedSearchInput.getSearchCriteriaList(),
				convertedSearchInput.getStart(), convertedSearchInput.getOffset());
	}

	public List<CompanyMsSearchBean> getCompany(String searchInput) throws IOException {
		CompanyMsSearchInputBean convertedSearchInput = (CompanyMsSearchInputBean) CompanyMsJsonService
				.convertJsonToObject(CompanyMsSearchInputBean.class, searchInput);
		return domainQueryDbSevice.queryCompany(convertedSearchInput.getSearchCriteriaList(),
				convertedSearchInput.getStart(), convertedSearchInput.getOffset());
	}

	public List<String> getDomainConstant(String domainConstantName) {
		return domainQueryDbSevice.getDomainConstantList(domainConstantName);
	}

	public boolean getDuplicateCompany(String aggregateId) {
		return domainQueryDbSevice.getDuplicateCompany(aggregateId);
	}

	public CompanyMsCompanyBean getCompanyDetails(String aggregateId) {
		return domainQueryDbSevice.getCompanyDetails(aggregateId);
	}

	public List<CompanyMsCompanyTradeClassBean> getCompanyTradeClass(String aggregateId) {
		return domainQueryDbSevice.getCompanyTradeClass();
	}

	public List<CompanyMsCompanyIdentifierBean> getCompanyIdentifier(String aggregateId) {
		return domainQueryDbSevice.getCompanyIdentifier();
	}

}
