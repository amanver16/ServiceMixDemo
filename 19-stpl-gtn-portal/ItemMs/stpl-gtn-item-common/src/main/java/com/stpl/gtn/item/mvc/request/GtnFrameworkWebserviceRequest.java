package com.stpl.gtn.item.mvc.request;

public class GtnFrameworkWebserviceRequest {

	public GtnFrameworkWebserviceRequest() {
		super();
	}

	private GtnWsSearchRequest gtnWsSearchRequest;
	private GtnWsItemDeleteRequest wsItemDeleteRequest;
	private ItemMsItemInventoryRequest itemInventoryRequest;

	public GtnWsSearchRequest getGtnWsSearchRequest() {
		return gtnWsSearchRequest;
	}

	public void setGtnWsSearchRequest(GtnWsSearchRequest gtnWsSearchRequest) {
		this.gtnWsSearchRequest = gtnWsSearchRequest;
	}

	public GtnWsItemDeleteRequest getWsItemDeleteRequest() {
		return wsItemDeleteRequest;
	}

	public void setWsItemDeleteRequest(GtnWsItemDeleteRequest wsItemDeleteRequest) {
		this.wsItemDeleteRequest = wsItemDeleteRequest;
	}

	public ItemMsItemInventoryRequest getItemInventoryRequest() {
		return itemInventoryRequest;
	}

	public void setItemInventoryRequest(ItemMsItemInventoryRequest itemInventoryRequest) {
		this.itemInventoryRequest = itemInventoryRequest;
	}

}
