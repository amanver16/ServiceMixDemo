package com.stpl.gtn.company.mvc.bus.producer;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.gtn.company.common.service.CompanyMsDateService;
import com.stpl.gtn.company.domain.command.CompanyMsGenericCommand;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsCommandProducer {

	public CompanyMsCommandProducer() {
		super();
	}

	@Produce(uri = "jms:topic:companyMsCommandBus")
	ProducerTemplate companyMsCommand;

	public void initiateCommand(CompanyMsGenericCommand command) {
		try {
			System.out.println("Company MS MVC Layer : Sending command with " + command.getCommandName() + " requestId "
					+ command.getOriginCommandRequestId());
			command.setIssuedTime(CompanyMsDateService.getCurrentDateInString());
			String commandJson = CompanyMsJsonService.convertObjectToJson(command);
			System.out.println("Company MS MVC Layer : CommandJson " + commandJson);
			companyMsCommand.sendBody(commandJson);
			System.out.println("Company MS MVC Layer : CommandSent ");

		} catch (CamelExecutionException e) {
			System.out.println(
					"Company MS MVC Layer : Error in sending command to the queue " + command.getCommandName());
			e.printStackTrace();
		}
	}

}
