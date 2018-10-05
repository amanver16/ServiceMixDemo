package com.stpl.gtn.item.domain.query.data.bean;

import java.util.List;

public class ItemMsItemBean {

	private String aggregateId;
	private ItemMsItemInformationBean itemMsItemInformationBean;
	private List<ItemMsItemIdentifierBean> itemMsItemIdentifierBeanList;
	private List<ItemMsItemPricingBean> msItemPricingBeanList;
	private ItemMsItemAdditionalInfoBean itemAdditionalInfoBean;
	private ItemMsItemNotesBean msItemNotesBean;

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public ItemMsItemInformationBean getItemMsItemInformationBean() {
		return itemMsItemInformationBean;
	}

	public void setItemMsItemInformationBean(ItemMsItemInformationBean itemMsItemInformationBean) {
		this.itemMsItemInformationBean = itemMsItemInformationBean;
	}

	public List<ItemMsItemIdentifierBean> getItemMsItemIdentifierBeanList() {
		return itemMsItemIdentifierBeanList;
	}

	public void setItemMsItemIdentifierBeanList(List<ItemMsItemIdentifierBean> itemMsItemIdentifierBeanList) {
		this.itemMsItemIdentifierBeanList = itemMsItemIdentifierBeanList;
	}

	public List<ItemMsItemPricingBean> getMsItemPricingBeanList() {
		return msItemPricingBeanList;
	}

	public void setMsItemPricingBeanList(List<ItemMsItemPricingBean> msItemPricingBeanList) {
		this.msItemPricingBeanList = msItemPricingBeanList;
	}

	public ItemMsItemAdditionalInfoBean getItemAdditionalInfoBean() {
		return itemAdditionalInfoBean;
	}

	public void setItemAdditionalInfoBean(ItemMsItemAdditionalInfoBean itemAdditionalInfoBean) {
		this.itemAdditionalInfoBean = itemAdditionalInfoBean;
	}

	public ItemMsItemNotesBean getMsItemNotesBean() {
		return msItemNotesBean;
	}

	public void setMsItemNotesBean(ItemMsItemNotesBean msItemNotesBean) {
		this.msItemNotesBean = msItemNotesBean;
	}

	public void initialiseBeans(ItemMsItemInformationBean itemMsItemInformationBean,
			List<ItemMsItemIdentifierBean> itemMsItemIdentifierBeanList,
			List<ItemMsItemPricingBean> msItemPricingBeanList, ItemMsItemAdditionalInfoBean itemAdditionalInfoBean,
			ItemMsItemNotesBean msItemNotesBean) {
		this.itemMsItemInformationBean = itemMsItemInformationBean;
		this.itemMsItemIdentifierBeanList = itemMsItemIdentifierBeanList;
		this.msItemPricingBeanList = msItemPricingBeanList;
		this.itemAdditionalInfoBean = itemAdditionalInfoBean;
		this.msItemNotesBean = msItemNotesBean;
	}
}
