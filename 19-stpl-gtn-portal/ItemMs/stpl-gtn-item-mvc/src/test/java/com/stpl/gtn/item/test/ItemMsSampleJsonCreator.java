package com.stpl.gtn.item.test;

import java.util.ArrayList;
import java.util.List;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemAdditionalInfoBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemIdentifierBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemInformationBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemNotesBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemPricingBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsSearchCriteria;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;
import com.stpl.gtn.item.mvc.request.GtnFrameworkWebserviceRequest;
import com.stpl.gtn.item.mvc.request.GtnWsSearchRequest;
import com.stpl.gtn.item.mvc.request.ItemMsItemInventoryRequest;

public class ItemMsSampleJsonCreator {

	public static void main(String args[]) {

		GtnFrameworkWebserviceRequest wsRequest = new GtnFrameworkWebserviceRequest();
		ItemMsItemBean itemMasterBean = new ItemMsItemBean();
		ItemMsItemInformationBean itemMsItemInformationBean = getItemInformation();
		List<ItemMsItemIdentifierBean> itemMsItemIdentifierBeanList = getIdentifierList();
		List<ItemMsItemPricingBean> msItemPricingBeanList = getPricingBean();
		ItemMsItemAdditionalInfoBean itemAdditionalInfoBean = getAdditonalInfo();
		ItemMsItemNotesBean msItemNotesBean = new ItemMsItemNotesBean();
		itemMasterBean.initialiseBeans(itemMsItemInformationBean, itemMsItemIdentifierBeanList, msItemPricingBeanList,
				itemAdditionalInfoBean, msItemNotesBean);

		// ItemMasterBean.setAggregateId("6cdccc3f-74dd-4654-89d7-59ca78c1a62b");

		getInventoryRequest(itemMasterBean, wsRequest);

		// searchRequestJson();
	}

	private static void getInventoryRequest(ItemMsItemBean itemMasterBean, GtnFrameworkWebserviceRequest wsRequest) {
		ItemMsItemInventoryRequest inventoryRequest = new ItemMsItemInventoryRequest();
		inventoryRequest.setItemBean(itemMasterBean);
		wsRequest.setItemInventoryRequest(inventoryRequest);
		System.out.println(ItemMsJsonService.convertObjectToJson(wsRequest));
	}

	public static ItemMsItemInformationBean getItemInformation() {
		ItemMsItemInformationBean informationBean = new ItemMsItemInformationBean();
		informationBean.setBatchId("B1");
		informationBean.setBrand("ALG");
		informationBean.setBrandId("ALG");
		informationBean.setCreatedBy("1");
		informationBean.setCreatedDate("3-12-2018");
		informationBean.setDisplayBrand("AlLERGON");
		informationBean.setFirstSaleDate("3-12-2018");
		informationBean.setForm("AlLERGON");
		informationBean.setItemCategory("AlLERGON");
		informationBean.setItemClass("AlLERGON");
		informationBean.setItemCode("AlLERGON");
		informationBean.setItemDescription("AlLERGON");
		informationBean.setItemEndDate("3-12-2018");
		informationBean.setItemId("AlLERGON");
		informationBean.setItemName("AlLERGON");
		informationBean.setItemNo("AlLERGON");
		informationBean.setItemStartDate("3-12-2018");
		informationBean.setItemStatus("ACTIVE");
		informationBean.setItemType("AlLERGON");
		informationBean.setItemTypeIndication("AlLERGON");
		informationBean.setLabelerCode("AlLERGON");
		informationBean.setManufacturerId("AlLERGON");
		informationBean.setManufacturerName("AlLERGON");
		informationBean.setModifiedBy("AlLERGON");
		informationBean.setModifiedDate("3-12-2018");
		informationBean.setNdc8("AlLERGON");
		informationBean.setNdc9("AlLERGON");
		informationBean.setOrganizationKey("AlLERGON");
		informationBean.setPackageSize("AlLERGON");
		informationBean.setPackageSizeCode("AlLERGON");
		informationBean.setPrimaryUom("AlLERGON");
		informationBean.setSecondaryUom("AlLERGON");
		informationBean.setStrength("AlLERGON");
		informationBean.setSystemId("AlLERGON");
		informationBean.setTherapeuticClass("AlLERGON");
		informationBean.setUdc1("AlLERGON");
		informationBean.setUdc2("AlLERGON");
		informationBean.setUdc3("AlLERGON");
		informationBean.setUdc4("AlLERGON");
		informationBean.setUdc5("AlLERGON");
		informationBean.setUdc6("AlLERGON");
		informationBean.setUpps("AlLERGON");
		informationBean.setStrength("AlLERGON");
		return informationBean;
	}

	public static List<ItemMsItemIdentifierBean> getIdentifierList() {
		List<ItemMsItemIdentifierBean> identifierList = new ArrayList<>();
		ItemMsItemIdentifierBean identifierBean = new ItemMsItemIdentifierBean();
		identifierBean.setCreatedBy("1");
		identifierBean.setCreatedDate("3-12-2018");
		identifierBean.setEntityCodeName("ENTITY");
		identifierBean.setEntityCodeNo("123");
		identifierBean.setIdentifierEndDate("3-12-2018");
		identifierBean.setIdentifierStartDate("3-12-2018");
		identifierBean.setIdentifierStatus("Active");
		identifierBean.setItemIdentifier("Inditifier");
		identifierBean.setItemQualifierName("Name");
		identifierBean.setModifiedBy("1");
		identifierBean.setModifiedDate("3-12-2018");
		identifierList.add(identifierBean);
		return identifierList;
	}

	public static List<ItemMsItemPricingBean> getPricingBean() {
		List<ItemMsItemPricingBean> pricingBeanList = new ArrayList<>();
		ItemMsItemPricingBean itemPricingBean = new ItemMsItemPricingBean();
		itemPricingBean.setCreatedBy("1");
		itemPricingBean.setCreatedDate("3-12-2018");
		itemPricingBean.setEndDate("3-12-2018");
		itemPricingBean.setEntityCode("Code001");
		itemPricingBean.setItemPrice("500");
		itemPricingBean.setItemUom("UOM");
		itemPricingBean.setModifiedBy("1");
		itemPricingBean.setModifiedDate("3-12-2018");
		itemPricingBean.setPricingQualifierName("Qualifier");
		itemPricingBean.setPricingStatus("A");
		itemPricingBean.setSource("ETL");
		itemPricingBean.setStartDate("3-12-2018");
		pricingBeanList.add(itemPricingBean);
		return pricingBeanList;
	}

	public static ItemMsItemAdditionalInfoBean getAdditonalInfo() {
		ItemMsItemAdditionalInfoBean additionalInfoBean = new ItemMsItemAdditionalInfoBean();
		additionalInfoBean.setAcquiredAmp("AMp");
		additionalInfoBean.setAcquiredBamp("AAmp");
		additionalInfoBean.setAcquisitionDate("3-12-2018");
		additionalInfoBean.setAuthorizedGenericEndDate("3-12-2018");
		additionalInfoBean.setAuthorizedGenericIndicator(true);
		additionalInfoBean.setAuthorizedGenericStartDate("3-12-2018");
		additionalInfoBean.setBaseCpi("Cpi");
		additionalInfoBean.setBaseCpiPeriod("4");
		additionalInfoBean.setClottingFactorIndicator(true);
		additionalInfoBean.setObraBamp("OB");
		return additionalInfoBean;
	}

	public static void searchRequestJson() {
		GtnFrameworkWebserviceRequest wsRequest = new GtnFrameworkWebserviceRequest();
		GtnWsSearchRequest gtnWsSearchRequest = new GtnWsSearchRequest();
		ItemMsSearchCriteria searchCriteria = new ItemMsSearchCriteria();
		List<ItemMsSearchCriteria> l = new ArrayList<>();
		l.add(searchCriteria);
		searchCriteria.setFieldId("ItemId");
		searchCriteria.setFilterValue1("CM_001");
		gtnWsSearchRequest.setSearchCriteriaList(l);
		wsRequest.setGtnWsSearchRequest(gtnWsSearchRequest);
		System.out.println(ItemMsJsonService.convertObjectToJson(wsRequest));
	}

}
