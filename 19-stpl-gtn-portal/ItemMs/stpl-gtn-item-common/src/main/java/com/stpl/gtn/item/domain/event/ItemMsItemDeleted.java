package com.stpl.gtn.item.domain.event;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsItemDeleted {

	private ItemMsItemBean itemMasterBean;

	public ItemMsItemDeleted() {
		super();
	}

	public ItemMsItemBean getItemMasterBean() {
		return itemMasterBean;
	}

	public void setItemMasterBean(ItemMsItemBean itemMasterBean) {
		this.itemMasterBean = itemMasterBean;
	}

}
