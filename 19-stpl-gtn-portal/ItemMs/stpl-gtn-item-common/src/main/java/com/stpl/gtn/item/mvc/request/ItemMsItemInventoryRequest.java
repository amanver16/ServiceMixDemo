package com.stpl.gtn.item.mvc.request;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsItemInventoryRequest {

	public ItemMsItemInventoryRequest() {
		super();
	}

	private ItemMsItemBean itemBean;

	public ItemMsItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemMsItemBean itemBean) {
		this.itemBean = itemBean;
	}

}
