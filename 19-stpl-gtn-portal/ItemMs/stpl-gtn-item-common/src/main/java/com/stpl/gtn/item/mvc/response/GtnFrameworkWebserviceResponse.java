package com.stpl.gtn.item.mvc.response;

import java.util.List;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class GtnFrameworkWebserviceResponse {

	public GtnFrameworkWebserviceResponse() {
		super();
	}

	private String status;
	private String failureReason;
	private boolean sucess;
	private Exception gtnGeneralException;
	private GtnSearchResponse gtnSearchResponse;
	private ItemMsItemInventoryResponse itemInventoryResponse;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public GtnSearchResponse getGtnSearchResponse() {
		return gtnSearchResponse;
	}

	public void setGtnSearchResponse(GtnSearchResponse gtnSearchResponse) {
		this.gtnSearchResponse = gtnSearchResponse;
	}

	public void addItemBeanToSearchResponse(List<ItemMsItemBean> itemBeanList) {
		gtnSearchResponse = new GtnSearchResponse();
		gtnSearchResponse.setItemBeanList(itemBeanList);

	}

	public ItemMsItemInventoryResponse getItemInventoryResponse() {
		return itemInventoryResponse;
	}

	public void setItemInventoryResponse(ItemMsItemInventoryResponse itemInventoryResponse) {
		this.itemInventoryResponse = itemInventoryResponse;
	}

	public Exception getGtnGeneralException() {
		return gtnGeneralException;
	}

	public void setGtnGeneralException(Exception gtnGeneralException) {
		this.gtnGeneralException = gtnGeneralException;
	}

}
