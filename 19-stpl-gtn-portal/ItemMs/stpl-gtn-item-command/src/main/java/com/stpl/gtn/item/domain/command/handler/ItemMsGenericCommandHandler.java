package com.stpl.gtn.item.domain.command.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stpl.gtn.item.bus.event.producer.ItemMsEventProducer;
import com.stpl.gtn.item.domain.aggregate.item.ItemMsItemAggregate;
import com.stpl.gtn.item.domain.aggregate.item.loader.ItemMsAggregateLoader;
import com.stpl.gtn.item.domain.aggregate.item.service.ItemMsService;
import com.stpl.gtn.item.domain.command.ItemMsCommandType;
import com.stpl.gtn.item.domain.command.ItemMsGenericCommand;
import com.stpl.gtn.item.domain.event.ItemMsGenericEvent;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsGenericCommandHandler {

	private ItemMsEventProducer itemMsEventProducer;

	private ItemMsAggregateLoader itemMsAggregateLoader;

	private ItemMsService itemMsService;

	private Map<String, ItemMsCommandType> supportedCommandMap;

	public ItemMsEventProducer getItemMsEventProducer() {
		return itemMsEventProducer;
	}

	public void setItemMsEventProducer(ItemMsEventProducer itemMsEventProducer) {
		this.itemMsEventProducer = itemMsEventProducer;
	}

	public ItemMsAggregateLoader getItemMsAggregateLoader() {
		return itemMsAggregateLoader;
	}

	public void setItemMsAggregateLoader(ItemMsAggregateLoader itemMsAggregateLoader) {
		this.itemMsAggregateLoader = itemMsAggregateLoader;
	}

	public ItemMsService getItemMsService() {
		return itemMsService;
	}

	public void setItemMsService(ItemMsService itemMsService) {
		this.itemMsService = itemMsService;
	}

	public Map<String, ItemMsCommandType> getSupportedCommandMap() {
		return supportedCommandMap;
	}

	public void setSupportedCommandMap(Map<String, ItemMsCommandType> supportedCommandMap) {
		this.supportedCommandMap = supportedCommandMap;
	}

	public ItemMsGenericCommandHandler() {
		supportedCommandMap = new HashMap<>();
		supportedCommandMap.put(ItemMsCommandType.DeleteItem.getCommandName(), ItemMsCommandType.DeleteItem);
		supportedCommandMap.put(ItemMsCommandType.AddItem.getCommandName(), ItemMsCommandType.AddItem);
		supportedCommandMap.put(ItemMsCommandType.UpdateItem.getCommandName(), ItemMsCommandType.UpdateItem);
	}

	public void handleCommand(String commandString) {

		System.out.println("Item MS : Command Handler : Receiving command " + commandString);
		try {

			ItemMsGenericCommand command = ItemMsJsonService.buildCommandFromJson(commandString);
			if (supportedCommandMap.get(command.getCommandName()) == null) {
				System.out.println("Item MS: CommandHandler: IgnoringCommand: " + command.getCommandName());
				return;
			}
			ItemMsItemAggregate aggregate = itemMsAggregateLoader.loadAggregate(command.getAggregateId());
			List<ItemMsGenericEvent> eventList = aggregate.handleCommand(command, itemMsService);
			for (ItemMsGenericEvent currentEvent : eventList) {
				itemMsEventProducer.raiseEvent(currentEvent);
			}

		} catch (Exception e) {
			System.out.println("Item MS Command Handler : Error in handling command : " + commandString);
			e.printStackTrace();
		}
	}

}
