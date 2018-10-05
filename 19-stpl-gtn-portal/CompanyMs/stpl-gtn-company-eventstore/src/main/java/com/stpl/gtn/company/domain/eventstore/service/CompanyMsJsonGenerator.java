package com.stpl.gtn.company.domain.eventstore.service;

import java.util.ArrayList;
import java.util.List;

import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdded;
import com.stpl.gtn.company.domain.event.CompanyMsEventType;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsAddressBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyIdentifierBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyInformationBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyTradeClassBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsNotesBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsParentCompanyBean;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;
import com.stpl.gtn.company.mvc.request.CompanyMsCompanyInventoryRequest;

public class CompanyMsJsonGenerator {
	public static void main(String args[]) {

		CompanyMsGenericEvent genericResponse = new CompanyMsGenericEvent();
		CompanyMsCompanyBean companyMasterBean = new CompanyMsCompanyBean();

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

		CompanyMsCompanyAdded cmAddedEventData = new CompanyMsCompanyAdded();
		genericResponse.setAggregateId("d7dba4a4-57ea-4999-8536-3d00eba1990f");
		genericResponse.setEventName(CompanyMsEventType.CompanyAdded.getEventName());
		genericResponse.setOriginCommandRequestId("d7dba4a4-57ea-4999-8536-3d00eba1990f");

		List<CompanyMsCompanyIdentifierBean> companyIdentifierBeanList = new ArrayList<>();
		List<CompanyMsCompanyTradeClassBean> companyTradeClassBeanList = new ArrayList<>();
		List<CompanyMsParentCompanyBean> parentCompanyBeanList = new ArrayList<>();
		companyIdentifierBeanList.add(companyIdentifierBean);
		companyTradeClassBeanList.add(companyTradeClassBean);
		parentCompanyBeanList.add(parentCompanyBean);

		companyMasterBean.setCompanyMasterBeans(companyInformationBean, addressBean, companyIdentifierBeanList,
				companyTradeClassBeanList, parentCompanyBeanList, companyMsNotesBean);
		cmAddedEventData.setCompanyMasterBean(companyMasterBean);
		genericResponse.setEventData(CompanyMsJsonService.convertObjectToJson(cmAddedEventData));
		CompanyMsCompanyInventoryRequest inventoryRequest = new CompanyMsCompanyInventoryRequest();
		inventoryRequest.setCompanyBean(companyMasterBean);
		System.out.println(CompanyMsJsonService.convertObjectToJson(inventoryRequest));
	}

}
