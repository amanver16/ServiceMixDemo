package com.stpl.hr.onboard.bus.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.onboard.common.service.OnboardMsDateService;
import com.stpl.hr.onboard.domain.command.OnboardMsGenericCommand;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

@Component
public class OnboardMsCommandProducer {
	@Produce(uri = "jms:topic:loginMsCommandBus")
	ProducerTemplate onboardMsCommand;

	public void initiateCommand(OnboardMsGenericCommand command) {
		try {
			System.out.println("Onboard MS MVC Layer : Sending command with " + command.getCommandName() + " requestId "
					+ command.getOriginCommandRequestId());
			command.setIssuedTime(OnboardMsDateService.getCurrentDateInString());
			String commandJson = OnboardMSJsonService.convertObjectToJson(command);
			System.out.println("Onboard MS MVC Layer : CommandJson " + commandJson);
			onboardMsCommand.sendBody(commandJson);
			System.out.println("Onboard MS MVC Layer : CommandSent ");

		} catch (CamelExecutionException e) {
			System.out.println(
					"Onboard MS MVC Layer : Error in sending command to the queue " + command.getCommandName());
			e.printStackTrace();
		}
	}

}
