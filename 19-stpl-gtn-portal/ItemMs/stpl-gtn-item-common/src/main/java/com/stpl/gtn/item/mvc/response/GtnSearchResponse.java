package com.stpl.gtn.item.mvc.response;

import java.util.List;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class GtnSearchResponse {

	public GtnSearchResponse() {
		super();
	}

	private List<ItemMsItemBean> itemBeanList;

	public List<ItemMsItemBean> getItemBeanList() {
		return itemBeanList;
	}

	public void setItemBeanList(List<ItemMsItemBean> itemBeanList) {
		this.itemBeanList = itemBeanList;
	}

}
