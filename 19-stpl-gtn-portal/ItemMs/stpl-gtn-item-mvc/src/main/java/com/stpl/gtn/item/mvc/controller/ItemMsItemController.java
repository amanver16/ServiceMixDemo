package com.stpl.gtn.item.mvc.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stpl.gtn.item.domain.command.ItemMsAddItem;
import com.stpl.gtn.item.domain.command.ItemMsCommandType;
import com.stpl.gtn.item.domain.command.ItemMsDeleteItem;
import com.stpl.gtn.item.domain.command.ItemMsGenericCommand;
import com.stpl.gtn.item.domain.event.ItemMsEventType;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.ItemMsItemAdded;
import com.stpl.gtn.item.domain.event.ItemMsItemAdditionFailed;
import com.stpl.gtn.item.domain.event.ItemMsItemDeleted;
import com.stpl.gtn.item.domain.event.ItemMsItemDeletionFailed;
import com.stpl.gtn.item.domain.event.ItemMsItemUpdated;
import com.stpl.gtn.item.domain.event.ItemMsItemUpdationFailed;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsSearchInputBean;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;
import com.stpl.gtn.item.mvc.bus.consumer.ItemMsEventTracker;
import com.stpl.gtn.item.mvc.bus.gateway.ItemMsQueryModelGateway;
import com.stpl.gtn.item.mvc.bus.producer.ItemMsCommandProducer;
import com.stpl.gtn.item.mvc.request.GtnFrameworkWebserviceRequest;
import com.stpl.gtn.item.mvc.request.GtnWsItemDeleteRequest;
import com.stpl.gtn.item.mvc.request.GtnWsSearchRequest;
import com.stpl.gtn.item.mvc.request.ItemMsItemInventoryRequest;
import com.stpl.gtn.item.mvc.response.GtnFrameworkWebserviceResponse;
import com.stpl.gtn.item.mvc.response.ItemMsItemInventoryResponse;

public class ItemMsItemController {

	public ItemMsItemController() {
		super();
	}

	private ItemMsCommandProducer itemMsCommandProducer;
	private ItemMsEventTracker itemMsEventTracker;
	private ItemMsQueryModelGateway itemMsQueryModelGateway;

	public void setItemMsCommandProducer(ItemMsCommandProducer itemMsCommandProducer) {
		this.itemMsCommandProducer = itemMsCommandProducer;
	}

	public void setItemMsEventTracker(ItemMsEventTracker itemMsEventTracker) {
		this.itemMsEventTracker = itemMsEventTracker;
	}

	public void setItemMsQueryModelGateway(ItemMsQueryModelGateway itemMsQueryModelGateway) {
		this.itemMsQueryModelGateway = itemMsQueryModelGateway;
	}

	@POST
	@Path("/ItemMsItemSaveService")
	@Produces(MediaType.APPLICATION_JSON)
	public String addItem(String itemDetails) {
		GtnFrameworkWebserviceResponse wsResponse = new GtnFrameworkWebserviceResponse();

		wsResponse.setStatus("Exception");
		wsResponse.setFailureReason("Event not received");
		try {
			GtnFrameworkWebserviceRequest webserviceRequest = (GtnFrameworkWebserviceRequest) ItemMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, itemDetails);

			ItemMsItemInventoryRequest itemInventoryRequest = webserviceRequest.getItemInventoryRequest();
			ItemMsGenericCommand genericCommand = buildItemInventoryCommand(itemInventoryRequest);
			itemMsCommandProducer.initiateCommand(genericCommand);
			itemMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			ItemMsGenericEvent eventResponse = itemMsEventTracker.pollEvent(genericCommand.getOriginCommandRequestId(),
					30);

			System.out.println("Item MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {
				buildItemMsResponse(wsResponse, eventResponse);
			}
		} catch (IOException e) {
			wsResponse.setFailureReason("Item MS MVC :Http Exception while processing addItem");
			e.printStackTrace();
		}
		String convertedResponse = ItemMsJsonService.convertObjectToJson(wsResponse);
		return convertedResponse;

	}

	private ItemMsGenericCommand buildItemInventoryCommand(ItemMsItemInventoryRequest itemMsInventorytRequest) {
		ItemMsAddItem initiateItemToInventoryCommand = new ItemMsAddItem();
		ItemMsItemBean itemBean = itemMsInventorytRequest.getItemBean();
		initiateItemToInventoryCommand.setItemMasterBean(itemBean);

		String commandData = ItemMsJsonService.convertObjectToJson(initiateItemToInventoryCommand);

		ItemMsGenericCommand genericCommand = new ItemMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(itemMsQueryModelGateway.getItemAggregateId());
		genericCommand.setCommandName(ItemMsCommandType.AddItem.getCommandName());
		if (itemBean.getAggregateId() != null) {
			genericCommand.setAggregateId(itemBean.getAggregateId());
			genericCommand.setCommandName(ItemMsCommandType.UpdateItem.getCommandName());
		}
		return genericCommand;
	}

	private void buildItemMsResponse(GtnFrameworkWebserviceResponse httpResponse, ItemMsGenericEvent eventResponse)
			throws IOException {
		ItemMsItemInventoryResponse itemInventoryResponse = new ItemMsItemInventoryResponse();
		httpResponse.setStatus("Failure");
		httpResponse.setFailureReason("Item Addition Failure");
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemAdded.getEventName())) {
			ItemMsItemAdded itemAddedEventData = (ItemMsItemAdded) ItemMsJsonService
					.convertJsonToObject(ItemMsItemAdded.class, eventResponse.getEventData());
			itemInventoryResponse.setItemBean(itemAddedEventData.getItemMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemAdditionFailed.getEventName())) {
			ItemMsItemAdditionFailed itemAdditionFailedEventData = (ItemMsItemAdditionFailed) ItemMsJsonService
					.convertJsonToObject(ItemMsItemAdditionFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(itemAdditionFailedEventData.getFailureReason());
		}
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemUpdated.getEventName())) {
			ItemMsItemUpdated itemUpdatedEventData = (ItemMsItemUpdated) ItemMsJsonService
					.convertJsonToObject(ItemMsItemUpdated.class, eventResponse.getEventData());
			itemInventoryResponse.setItemBean(itemUpdatedEventData.getItemMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemUpdationFailed.getEventName())) {
			ItemMsItemUpdationFailed itemUpdationFailedEventData = (ItemMsItemUpdationFailed) ItemMsJsonService
					.convertJsonToObject(ItemMsItemUpdationFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(itemUpdationFailedEventData.getFailureReason());
		}
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemDeleted.getEventName())) {
			ItemMsItemDeleted itemDeletedEventData = (ItemMsItemDeleted) ItemMsJsonService
					.convertJsonToObject(ItemMsItemDeleted.class, eventResponse.getEventData());
			itemInventoryResponse.setItemBean(itemDeletedEventData.getItemMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
			httpResponse.setSucess(true);
		}
		if (eventResponse.getEventName().equals(ItemMsEventType.ItemDeletionFailed.getEventName())) {
			ItemMsItemDeletionFailed itemDeletionFailedEventData = (ItemMsItemDeletionFailed) ItemMsJsonService
					.convertJsonToObject(ItemMsItemDeletionFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(itemDeletionFailedEventData.getFailureReason());
		}
		httpResponse.setItemInventoryResponse(itemInventoryResponse);
	}

	@POST
	@Path("/ItemMsItemSearchService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getItems(String searchCriteria) {
		GtnFrameworkWebserviceResponse wsResponse = new GtnFrameworkWebserviceResponse();
		wsResponse.setStatus("Exception");
		wsResponse.setFailureReason("Event not received");
		System.out.println("ItemMS : MVC Layer: Inside getItems ");
		try {
			GtnFrameworkWebserviceRequest wsRequest = (GtnFrameworkWebserviceRequest) ItemMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, searchCriteria);
			GtnWsSearchRequest gtnWsSearchRequest = wsRequest.getGtnWsSearchRequest();

			ItemMsSearchInputBean searchInputBean = new ItemMsSearchInputBean();
			searchInputBean.setSearchCriteriaList(gtnWsSearchRequest.getSearchCriteriaList());
			searchInputBean.setOffset(gtnWsSearchRequest.getTableRecordOffset());
			searchInputBean.setStart(gtnWsSearchRequest.getTableRecordStart());
			String searchCriteriaList = ItemMsJsonService.convertObjectToJson(searchInputBean);
			List<ItemMsItemBean> associateList = itemMsQueryModelGateway.getItems(searchCriteriaList);
			wsResponse.addItemBeanToSearchResponse(associateList);
			wsResponse.setStatus("Success");
			wsResponse.setFailureReason("");
			wsResponse.setSucess(true);
		} catch (IOException e) {
			wsResponse.setSucess(false);
			wsResponse.setFailureReason("Item MS MVC :Http Exception while processing getItems");
			e.printStackTrace();
		}
		String convertedResponse = ItemMsJsonService.convertObjectToJson(wsResponse);
		System.out.println("ComapnyMS : MVC Layer: getItems " + convertedResponse);
		return convertedResponse;
	}

	@POST
	@Path("/ItemMsItemDeleteService")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteItem(String itemBean) {
		GtnFrameworkWebserviceResponse httpResponse = new GtnFrameworkWebserviceResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");
		System.out.println("ItemMS : MVC Layer: Inside deleteItem ");
		try {
			GtnFrameworkWebserviceRequest wsRequest = (GtnFrameworkWebserviceRequest) ItemMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, itemBean);
			GtnWsItemDeleteRequest wsItemDeleteRequest = wsRequest.getWsItemDeleteRequest();

			ItemMsGenericCommand genericCommand = buildDeleteItemCommand(wsItemDeleteRequest);
			itemMsCommandProducer.initiateCommand(genericCommand);
			itemMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			ItemMsGenericEvent eventResponse = itemMsEventTracker.pollEvent(genericCommand.getOriginCommandRequestId(),
					30);

			System.out.println("Item MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {
				buildItemMsResponse(httpResponse, eventResponse);
			}
		} catch (IOException e) {
			httpResponse.setSucess(false);
			httpResponse.setFailureReason("Item MS MVC :Http Exception while processing delete item");
			e.printStackTrace();
		}
		String convertedResponse = ItemMsJsonService.convertObjectToJson(httpResponse);
		System.out.println("ComapnyMS : MVC Layer: getItems " + convertedResponse);
		return convertedResponse;
	}

	private ItemMsGenericCommand buildDeleteItemCommand(GtnWsItemDeleteRequest wsItemDeleteRequest) {
		ItemMsDeleteItem deleteItemCommand = new ItemMsDeleteItem();
		ItemMsItemBean cmBean = wsItemDeleteRequest.getItemBean();
		deleteItemCommand.setItemMasterBean(cmBean);
		String commandData = ItemMsJsonService.convertObjectToJson(deleteItemCommand);
		ItemMsGenericCommand genericCommand = new ItemMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(cmBean.getAggregateId());
		genericCommand.setCommandName(ItemMsCommandType.DeleteItem.getCommandName());
		return genericCommand;
	}

	@POST
	@Path("/ItemMsItemDetailsService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getItemDetails(String itemDetailsRequest) {
		GtnFrameworkWebserviceResponse httpResponse = new GtnFrameworkWebserviceResponse();
		ItemMsItemInventoryResponse inventoryResponse = new ItemMsItemInventoryResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");
		System.out.println("ItemMS : MVC Layer: Inside getItemDetails ");
		try {
			GtnFrameworkWebserviceRequest webserviceRequest = (GtnFrameworkWebserviceRequest) ItemMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, itemDetailsRequest);

			ItemMsItemInventoryRequest itemInventoryRequest = webserviceRequest.getItemInventoryRequest();
			ItemMsItemBean itemBean = itemInventoryRequest.getItemBean();
			ItemMsItemBean itemDetails = itemMsQueryModelGateway.getItemDetails(itemBean.getAggregateId());
			inventoryResponse.setItemBean(itemDetails);
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
			httpResponse.setSucess(true);
		} catch (IOException e) {
			httpResponse.setSucess(false);
			httpResponse.setFailureReason("Item MS MVC :Http Exception while processing getItems");
			e.printStackTrace();
		}
		httpResponse.setItemInventoryResponse(inventoryResponse);
		String convertedResponse = ItemMsJsonService.convertObjectToJson(httpResponse);
		System.out.println("ItemMS : MVC Layer: getItems " + convertedResponse);
		return convertedResponse;
	}
}
