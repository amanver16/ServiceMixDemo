package com.stpl.gtn.item.mvc.response;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsItemInventoryResponse {

	public ItemMsItemInventoryResponse() {
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
