package com.stpl.gtn.item.domain.event;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsItemAdded {

	private ItemMsItemBean itemMasterBean;

	public ItemMsItemAdded() {
		super();
	}

	public ItemMsItemBean getItemMasterBean() {
		return itemMasterBean;
	}

	public void setItemMasterBean(ItemMsItemBean itemMasterBean) {
		this.itemMasterBean = itemMasterBean;
	}

}
