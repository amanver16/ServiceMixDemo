package com.stpl.gtn.item.mvc.bus.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.item.common.service.ItemMsDateService;
import com.stpl.gtn.item.domain.command.ItemMsGenericCommand;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsCommandProducer {

	public ItemMsCommandProducer() {
		super();
	}

	@Produce(uri = "jms:topic:itemMsCommandBus")
	ProducerTemplate itemMsCommand;

	public void initiateCommand(ItemMsGenericCommand command) {
		try {
			System.out.println("Item MS MVC Layer : Sending command with " + command.getCommandName() + " requestId "
					+ command.getOriginCommandRequestId());
			command.setIssuedTime(ItemMsDateService.getCurrentDateInString());
			String commandJson = ItemMsJsonService.convertObjectToJson(command);
			System.out.println("Item MS MVC Layer : CommandJson " + commandJson);
			itemMsCommand.sendBody(commandJson);
			System.out.println("Item MS MVC Layer : CommandSent ");

		} catch (CamelExecutionException e) {
			System.out.println("Item MS MVC Layer : Error in sending command to the queue " + command.getCommandName());
			e.printStackTrace();
		}
	}

}
