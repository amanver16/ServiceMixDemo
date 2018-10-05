package com.stpl.hr.asset.bus.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.asset.common.service.AssetMsDateService;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsCommandProducer {

	public AssetMsCommandProducer() {
		super();
	}

	@Produce(uri = "jms:topic:loginMsCommandBus")
	ProducerTemplate onboardMsCommand;

	public void initiateCommand(AssetMsGenericCommand command) {
		try {
			System.out.println("Asset MS MVC Layer : Sending command with " + command.getCommandName() + " requestId "
					+ command.getOriginCommandRequestId());
			command.setIssuedTime(AssetMsDateService.getCurrentDateInString());
			String commandJson = AssetMSJsonService.convertObjectToJson(command);
			System.out.println("Asset MS MVC Layer : CommandJson " + commandJson);
			onboardMsCommand.sendBody(commandJson);
			System.out.println("Asset MS MVC Layer : CommandSent ");

		} catch (CamelExecutionException e) {
			System.out
					.println("Asset MS MVC Layer : Error in sending command to the queue " + command.getCommandName());
			e.printStackTrace();
		}
	}

}
