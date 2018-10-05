package com.stpl.hr.login.bus.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.login.common.service.LoginMsDateService;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

@Component
public class LoginMsCommandProducer {

	@Produce(uri = "jms:topic:loginMsCommandBus")
	ProducerTemplate loginMsCommand;

	public void initiateCommand(LoginMsGenericCommand command) {
		try {
			System.out.println("LoginMs : MVC Layer : Sending command with " + command.getCommandName() + " requestId "
					+ command.getOriginCommandRequestId());
			command.setIssuedTime(LoginMsDateService.getCurrentDateInString());
			String commandJson = LoginMSJsonService.convertObjectToJson(command);
			System.out.println("LoginMs : MVC Layer : CommandJson " + commandJson);
			loginMsCommand.sendBody(commandJson);
			System.out.println("LoginMs : MVC Layer : CommandSent ");

		} catch (CamelExecutionException e) {
			System.out
					.println("LoginMs : MVC Layer : Error in sending command to the queue " + command.getCommandName());
			e.printStackTrace();
		}
	}

}
