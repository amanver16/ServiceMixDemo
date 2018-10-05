package com.stpl.gtn.item.domain.aggregate.item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stpl.gtn.item.domain.aggregate.ItemMsAggregateType;
import com.stpl.gtn.item.domain.aggregate.item.service.ItemMsService;
import com.stpl.gtn.item.domain.command.ItemMsAddItem;
import com.stpl.gtn.item.domain.command.ItemMsCommandType;
import com.stpl.gtn.item.domain.command.ItemMsDeleteItem;
import com.stpl.gtn.item.domain.command.ItemMsGenericCommand;
import com.stpl.gtn.item.domain.command.ItemMsUpdateItem;
import com.stpl.gtn.item.domain.event.ItemMsEventType;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.event.ItemMsItemAdded;
import com.stpl.gtn.item.domain.event.ItemMsItemAdditionFailed;
import com.stpl.gtn.item.domain.event.ItemMsItemDeleted;
import com.stpl.gtn.item.domain.event.ItemMsItemDeletionFailed;
import com.stpl.gtn.item.domain.event.ItemMsItemUpdated;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsItemAggregate {
	private String aggregateId;

	private ItemMsItemBean itemBean;

	public ItemMsItemAggregate(String aggregateId) {
		super();
		this.aggregateId = aggregateId;
	}

	public void applyEvent(ItemMsGenericEvent eventToBeApplied) throws IOException {

		this.aggregateId = eventToBeApplied.getAggregateId();
		if (eventToBeApplied.getEventName().equals(ItemMsEventType.ItemAdded.getEventName())) {

			ItemMsItemAdded itemAdded = (ItemMsItemAdded) ItemMsJsonService.convertJsonToObject(ItemMsItemAdded.class,
					eventToBeApplied.getEventData());
			this.itemBean = itemAdded.getItemMasterBean();
			return;

		}

		return;

	}

	public List<ItemMsGenericEvent> handleCommand(ItemMsGenericCommand genericCommand, ItemMsService itemMsService)
			throws IOException {

		List<ItemMsGenericEvent> eventList = new ArrayList<>();

		if (genericCommand.getCommandName().equals(ItemMsCommandType.AddItem.getCommandName())) {

			ItemMsAddItem addItem = (ItemMsAddItem) ItemMsJsonService.convertJsonToObject(ItemMsAddItem.class,
					genericCommand.getCommandData());

			addItemAddedEvent(eventList, genericCommand, addItem, itemMsService);

		}
		if (genericCommand.getCommandName().equals(ItemMsCommandType.UpdateItem.getCommandName())) {

			ItemMsUpdateItem updateItem = (ItemMsUpdateItem) ItemMsJsonService
					.convertJsonToObject(ItemMsUpdateItem.class, genericCommand.getCommandData());
			ItemMsItemBean itemMasterBean = updateItem.getItemMasterBean();

			if (itemMasterBean.getAggregateId() == null) {
				System.out.println("Item MS : Domain Command : aggregateId not present for update.");
				addItemUpdatingFailedEvent(eventList, genericCommand, updateItem);
			} else {
				this.itemBean = updateItem.getItemMasterBean();
				addItemUpdatedEvent(eventList, genericCommand, updateItem, itemMsService);
			}

		}
		if (genericCommand.getCommandName().equals(ItemMsCommandType.DeleteItem.getCommandName())) {

			ItemMsDeleteItem deleteItem = (ItemMsDeleteItem) ItemMsJsonService
					.convertJsonToObject(ItemMsDeleteItem.class, genericCommand.getCommandData());
			ItemMsItemBean itemMasterBean = deleteItem.getItemMasterBean();

			if (itemMasterBean.getAggregateId() == null) {
				System.out.println("Item MS : Domain Command : aggregateId not present for delete.");
				addItemDeletingFailedEvent(eventList, genericCommand, deleteItem);
			} else {
				this.itemBean = deleteItem.getItemMasterBean();
				addItemDeletedEvent(eventList, genericCommand, deleteItem, itemMsService);
			}

		}

		return eventList;

	}

	private void addItemAddedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsAddItem addItemToInventory, ItemMsService itemMsService) {

		ItemMsItemAdded itemAddedEvent = new ItemMsItemAdded();
		itemAddedEvent.setItemMasterBean(addItemToInventory.getItemMasterBean());

		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemAdded, itemAddedEvent);
		eventList.add(genericEvent);

	}

	private void addItemAddingFailedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsAddItem addItemToInventory) {

		ItemMsItemAdditionFailed itemAddingFailed = new ItemMsItemAdditionFailed();

		itemAddingFailed.setFailureReason("Item  is not valid");
		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemAdditionFailed,
				itemAddingFailed);
		eventList.add(genericEvent);

	}

	private ItemMsGenericEvent buildGenericEvent(ItemMsGenericCommand genericCommand, ItemMsEventType eventType,
			Object eventObject) {
		ItemMsGenericEvent genericEvent = new ItemMsGenericEvent();
		genericEvent.setAggregateId(this.aggregateId);
		genericEvent.setAggregateType(ItemMsAggregateType.ItemAggregate.getAggregateType());
		genericEvent.setEventName(eventType.getEventName());
		genericEvent.setOriginAggregateId(genericCommand.getAggregateId());
		genericEvent.setOriginCommandRequestId(genericCommand.getOriginCommandRequestId());
		genericEvent.setOriginCommandName(genericCommand.getCommandName());
		genericEvent.setOriginIssuedTime(genericCommand.getIssuedTime());
		genericEvent.setEventData(ItemMsJsonService.convertObjectToJson(eventObject));
		return genericEvent;
	}

	private void addItemUpdatingFailedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsUpdateItem updateItem) {
		ItemMsItemAdditionFailed itemUpdatingFailed = new ItemMsItemAdditionFailed();

		itemUpdatingFailed.setFailureReason("Item aggregateId is not present for update.");
		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemUpdationFailed,
				itemUpdatingFailed);
		eventList.add(genericEvent);
	}

	private void addItemUpdatedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsUpdateItem updateItem, ItemMsService itemMsService) {

		ItemMsItemUpdated itemUpdatedEvent = new ItemMsItemUpdated();
		itemUpdatedEvent.setItemMasterBean(updateItem.getItemMasterBean());
		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemUpdated,
				itemUpdatedEvent);
		eventList.add(genericEvent);

	}

	private void addItemDeletedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsDeleteItem deleteItem, ItemMsService itemMsService) {
		ItemMsItemDeleted itemDeletedEvent = new ItemMsItemDeleted();
		itemDeletedEvent.setItemMasterBean(deleteItem.getItemMasterBean());
		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemDeleted,
				itemDeletedEvent);
		eventList.add(genericEvent);
	}

	private void addItemDeletingFailedEvent(List<ItemMsGenericEvent> eventList, ItemMsGenericCommand genericCommand,
			ItemMsDeleteItem deleteItem) {
		ItemMsItemDeletionFailed itemDeletingFailed = new ItemMsItemDeletionFailed();

		itemDeletingFailed.setFailureReason("Item aggregateId is not present for delete.");
		ItemMsGenericEvent genericEvent = buildGenericEvent(genericCommand, ItemMsEventType.ItemDeletionFailed,
				itemDeletingFailed);
		eventList.add(genericEvent);
	}
}
