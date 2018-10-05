package com.stpl.gtn.item.mvc.bus.gateway;

import java.util.List;
import java.util.UUID;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;

public class ItemMsQueryModelGateway {
	public ItemMsQueryModelGateway() {
		super();
	}

	@Produce(uri = "direct-vm:checkDuplicateItem")
	private ProducerTemplate duplicateCheckTemplate;

	@Produce(uri = "direct-vm:queryGetItems")
	private ProducerTemplate queryGetCompanies;

	@Produce(uri = "direct-vm:getItemDetails")
	private ProducerTemplate queryGetItemDetails;

	public String getItemAggregateId() {
		String newAggregateId = UUID.randomUUID().toString();
		System.out.println("Item MS MVC Layer: Generating New AggregateId for new Item " + newAggregateId);
		return newAggregateId;
	}

	public boolean isItemIdDuplicate(String itemId) {
		Object response = duplicateCheckTemplate.requestBody(itemId);
		boolean isDuplicate = (boolean) response;
		System.out.println("Item MS MVC Layer:isItemIdDuplicate - " + isDuplicate);
		return isDuplicate;
	}

	public List<ItemMsItemBean> getItems(String searchCriteria) {
		System.out.println("Item MS MVC Layer:getCompanies - " + searchCriteria);
		Object response = queryGetCompanies.requestBody(searchCriteria);
		List<ItemMsItemBean> itemList = (List<ItemMsItemBean>) response;
		System.out.println("Item MS MVC Layer:getCompanies Response- " + itemList);
		return itemList;
	}

	public ItemMsItemBean getItemDetails(String aggregateId) {
		System.out.println("Item MS MVC Layer:getItemDetails - " + aggregateId);
		Object response = queryGetItemDetails.requestBody(aggregateId);
		ItemMsItemBean itemDetailsBean = (ItemMsItemBean) response;
		System.out.println("Item MS MVC Layer:getItemDetails Response- " + itemDetailsBean);
		return itemDetailsBean;
	}

}
