package com.stpl.gtn.company.domain.command.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stpl.gtn.company.bus.event.producer.CompanyMsEventProducer;
import com.stpl.gtn.company.domain.aggregate.company.CompanyMsCompanyAggregate;
import com.stpl.gtn.company.domain.aggregate.company.loader.CompanyMsAggregateLoader;
import com.stpl.gtn.company.domain.aggregate.company.service.CompanyMsService;
import com.stpl.gtn.company.domain.command.CompanyMsCommandType;
import com.stpl.gtn.company.domain.command.CompanyMsGenericCommand;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsGenericCommandHandler {

	private CompanyMsEventProducer companyMsEventProducer;

	private CompanyMsAggregateLoader companyMsAggregateLoader;

	private CompanyMsService companyMsService;

	private Map<String, CompanyMsCommandType> supportedCommandMap;

	public CompanyMsEventProducer getCompanyMsEventProducer() {
		return companyMsEventProducer;
	}

	public void setCompanyMsEventProducer(CompanyMsEventProducer companyMsEventProducer) {
		this.companyMsEventProducer = companyMsEventProducer;
	}

	public CompanyMsAggregateLoader getCompanyMsAggregateLoader() {
		return companyMsAggregateLoader;
	}

	public void setCompanyMsAggregateLoader(CompanyMsAggregateLoader companyMsAggregateLoader) {
		this.companyMsAggregateLoader = companyMsAggregateLoader;
	}

	public CompanyMsService getCompanyMsService() {
		return companyMsService;
	}

	public void setCompanyMsService(CompanyMsService companyMsService) {
		this.companyMsService = companyMsService;
	}

	public Map<String, CompanyMsCommandType> getSupportedCommandMap() {
		return supportedCommandMap;
	}

	public void setSupportedCommandMap(Map<String, CompanyMsCommandType> supportedCommandMap) {
		this.supportedCommandMap = supportedCommandMap;
	}

	public CompanyMsGenericCommandHandler() {
		supportedCommandMap = new HashMap<>();
		supportedCommandMap.put(CompanyMsCommandType.DeleteCompany.getCommandName(),
				CompanyMsCommandType.DeleteCompany);
		supportedCommandMap.put(CompanyMsCommandType.AddCompany.getCommandName(), CompanyMsCommandType.AddCompany);
		supportedCommandMap.put(CompanyMsCommandType.UpdateCompany.getCommandName(),
				CompanyMsCommandType.UpdateCompany);
	}

	public void handle(String commandString) {

		System.out.println("Company MS : Command Handler : Receiving command " + commandString);
		try {

			CompanyMsGenericCommand command = CompanyMsJsonService.buildCommandFromJson(commandString);
			if (supportedCommandMap.get(command.getCommandName()) == null) {
				System.out.println("Company MS: CommandHandler: IgnoringCommand: " + command.getCommandName());
				return;
			}
			CompanyMsCompanyAggregate aggregate = companyMsAggregateLoader.loadAggregate(command.getAggregateId());
			List<CompanyMsGenericEvent> eventList = aggregate.handleCommand(command, companyMsService);
			for (CompanyMsGenericEvent currentEvent : eventList) {
				companyMsEventProducer.raiseEvent(currentEvent);
			}

		} catch (Exception e) {
			System.out.println("Company MS Command Handler : Error in handling command : " + commandString);
			e.printStackTrace();
		}
	}

}
