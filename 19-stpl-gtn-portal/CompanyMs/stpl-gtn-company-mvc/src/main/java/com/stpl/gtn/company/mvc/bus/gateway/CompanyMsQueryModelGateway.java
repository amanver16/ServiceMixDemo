package com.stpl.gtn.company.mvc.bus.gateway;

import java.util.List;
import java.util.UUID;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;

public class CompanyMsQueryModelGateway {
	public CompanyMsQueryModelGateway() {
		super();
	}

	@Produce(uri = "direct-vm:checkDuplicateCompany")
	private ProducerTemplate duplicateCheckTemplate;

	@Produce(uri = "direct-vm:queryGetCompanies")
	private ProducerTemplate queryGetCompanies;

	@Produce(uri = "direct-vm:getCompanyDetails")
	private ProducerTemplate queryGetCompanyDetails;

	public String getCompanyAggregateId() {
		String newAggregateId = UUID.randomUUID().toString();
		System.out.println("Company MS MVC Layer: Generating New AggregateId for new Company " + newAggregateId);
		return newAggregateId;
	}

	public boolean isCompanyIdDuplicate(String companyId) {
		Object response = duplicateCheckTemplate.requestBody(companyId);
		boolean isDuplicate = (boolean) response;
		System.out.println("Company MS MVC Layer:isCompanyIdDuplicate - " + isDuplicate);
		return isDuplicate;
	}

	public List<CompanyMsSearchBean> getCompanies(String searchCriteria) {
		System.out.println("Company MS MVC Layer:getCompanies - " + searchCriteria);
		Object response = queryGetCompanies.requestBody(searchCriteria);
		List<CompanyMsSearchBean> assetList = (List<CompanyMsSearchBean>) response;
		System.out.println("Company MS MVC Layer:getCompanies Response- " + assetList);
		return assetList;
	}

	public CompanyMsCompanyBean getCompanyDetails(String aggregateId) {
		System.out.println("Company MS MVC Layer:getCompanyDetails - " + aggregateId);
		Object response = queryGetCompanyDetails.requestBody(aggregateId);
		CompanyMsCompanyBean companyDetailsBean = (CompanyMsCompanyBean) response;
		System.out.println("Company MS MVC Layer:getCompanyDetails Response- " + companyDetailsBean);
		return companyDetailsBean;
	}

}
