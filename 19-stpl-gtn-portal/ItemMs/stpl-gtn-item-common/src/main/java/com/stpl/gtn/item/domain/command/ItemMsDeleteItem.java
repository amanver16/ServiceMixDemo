package com.stpl.gtn.item.domain.command;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsDeleteItem {
	private ItemMsItemBean itemMasterBean;

	public ItemMsDeleteItem() {
		super();
	}

	public ItemMsItemBean getItemMasterBean() {
		return itemMasterBean;
	}

	public void setItemMasterBean(ItemMsItemBean itemMasterBean) {
		this.itemMasterBean = itemMasterBean;
	}

}
