package com.stpl.gtn.company.test;

import java.util.ArrayList;
import java.util.List;

import com.stpl.gtn.company.domain.query.data.bean.CompanyMsAddressBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyIdentifierBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyInformationBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyTradeClassBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsNotesBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsParentCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchCriteria;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;
import com.stpl.gtn.company.mvc.request.CompanyMsCompanyInventoryRequest;
import com.stpl.gtn.company.mvc.request.GtnFrameworkWebserviceRequest;
import com.stpl.gtn.company.mvc.request.GtnWsCompanyDeleteRequest;
import com.stpl.gtn.company.mvc.request.GtnWsSearchRequest;

public class CompanyMsSampleJsonCreator {

	public static void main(String args[]) {

		GtnFrameworkWebserviceRequest wsRequest = new GtnFrameworkWebserviceRequest();
		GtnWsCompanyDeleteRequest gtnWsDeleteRequest = new GtnWsCompanyDeleteRequest();
		CompanyMsCompanyBean companyMasterBean = new CompanyMsCompanyBean();
		gtnWsDeleteRequest.setCompanyBean(companyMasterBean);
		companyMasterBean.setAggregateId("6cdccc3f-74dd-4654-89d7-59ca78c1a62b");
		CompanyMsCompanyInformationBean companyInformationBean = new CompanyMsCompanyInformationBean();
		CompanyMsAddressBean addressBean = new CompanyMsAddressBean();
		CompanyMsCompanyIdentifierBean companyIdentifierBean = new CompanyMsCompanyIdentifierBean();
		CompanyMsCompanyTradeClassBean companyTradeClassBean = new CompanyMsCompanyTradeClassBean();
		CompanyMsParentCompanyBean parentCompanyBean = new CompanyMsParentCompanyBean();
		CompanyMsNotesBean companyMsNotesBean = new CompanyMsNotesBean();

		companyInformationBean.setCompanyId("CM_001");
		companyInformationBean.setCompanyCategory("GLComp");
		companyInformationBean.setCompanyEndDate("2-28-2018");
		companyInformationBean.setCompanyGroup("AA");
		companyInformationBean.setCompanyName("CM_001");
		companyInformationBean.setCompanyNo("CM_001");
		companyInformationBean.setCompanyStartDate("2-28-2018");
		companyInformationBean.setCompanyStatus("Active");
		companyInformationBean.setCompanyType("GL");
		companyInformationBean.setCreatedBy("2");
		companyInformationBean.setCreatedDate("2-28-2018");
		companyInformationBean.setFinancialSystem("F");
		companyInformationBean.setModifiedBy("1");
		companyInformationBean.setModifiedDate("2-28-2018");
		companyInformationBean.setOrganizationKey("ORG");
		companyInformationBean.setSource("ETL");
		companyInformationBean.setSystemId("123456");
		companyInformationBean.setUdc1("udc1");
		companyInformationBean.setUdc2("udc2");
		companyInformationBean.setUdc3("udc3");

		List<CompanyMsCompanyIdentifierBean> companyIdentifierBeanList = new ArrayList<>();
		List<CompanyMsCompanyTradeClassBean> companyTradeClassBeanList = new ArrayList<>();
		List<CompanyMsParentCompanyBean> parentCompanyBeanList = new ArrayList<>();
		companyIdentifierBeanList.add(companyIdentifierBean);
		companyTradeClassBeanList.add(companyTradeClassBean);
		parentCompanyBeanList.add(parentCompanyBean);

		companyMasterBean.setCompanyMasterBeans(companyInformationBean, addressBean, companyIdentifierBeanList,
				companyTradeClassBeanList, parentCompanyBeanList, companyMsNotesBean);

		// wsRequest.setWsCompanyDeleteRequest(gtnWsDeleteRequest);
		CompanyMsCompanyInventoryRequest inventoryRequest = new CompanyMsCompanyInventoryRequest();
		inventoryRequest.setCompanyBean(companyMasterBean);
		wsRequest.setCompanyInventoryRequest(inventoryRequest);
		// System.out.println(CompanyMsJsonService.convertObjectToJson(wsRequest));
		searchRequestJson();
	}

	public static void searchRequestJson() {
		GtnFrameworkWebserviceRequest wsRequest = new GtnFrameworkWebserviceRequest();
		GtnWsSearchRequest gtnWsSearchRequest = new GtnWsSearchRequest();
		CompanyMsSearchCriteria searchCriteria = new CompanyMsSearchCriteria();
		List<CompanyMsSearchCriteria> l = new ArrayList<>();
		l.add(searchCriteria);
		searchCriteria.setFieldId("companyId");
		searchCriteria.setFilterValue1("CM_001");
		gtnWsSearchRequest.setSearchCriteriaList(l);
		wsRequest.setGtnWsSearchRequest(gtnWsSearchRequest);
		System.out.println(CompanyMsJsonService.convertObjectToJson(wsRequest));
	}

}
