package com.stpl.gtn.item.domain.eventstore.service;

import com.stpl.gtn.item.domain.event.ItemMsEventType;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.ItemMsItemAdded;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;
import com.stpl.gtn.item.mvc.request.ItemMsItemInventoryRequest;

public class ItemMsJsonGenerator {
	public static void main(String args[]) {

		ItemMsGenericEvent genericResponse = new ItemMsGenericEvent();
		ItemMsItemBean itemMasterBean = new ItemMsItemBean();

		ItemMsItemAdded cmAddedEventData = new ItemMsItemAdded();
		genericResponse.setAggregateId("d7dba4a4-57ea-4999-8536-3d00eba1990f");
		genericResponse.setEventName(ItemMsEventType.ItemAdded.getEventName());
		genericResponse.setOriginCommandRequestId("d7dba4a4-57ea-4999-8536-3d00eba1990f");

		cmAddedEventData.setItemMasterBean(itemMasterBean);
		genericResponse.setEventData(ItemMsJsonService.convertObjectToJson(cmAddedEventData));
		ItemMsItemInventoryRequest inventoryRequest = new ItemMsItemInventoryRequest();
		inventoryRequest.setItemBean(itemMasterBean);
		System.out.println(ItemMsJsonService.convertObjectToJson(inventoryRequest));
	}

}
