package com.stpl.hr.onboard.domain.command.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stpl.hr.onboard.bus.event.producer.OnboardMsEventProducer;
import com.stpl.hr.onboard.domain.aggregate.associate.OnboardMsAssociateAggregate;
import com.stpl.hr.onboard.domain.aggregate.associate.loader.OnboardMsAggregateLoader;
import com.stpl.hr.onboard.domain.aggregate.associate.service.OnboardMsAssociateService;
import com.stpl.hr.onboard.domain.command.OnboardMsCommandType;
import com.stpl.hr.onboard.domain.command.OnboardMsGenericCommand;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsGenericCommandHandler {

	private OnboardMsEventProducer onboardMsEventProducer;

	private OnboardMsAggregateLoader onboardMsAggregateLoader;

	private OnboardMsAssociateService onboardMsAssociateService;

	private Map<String, OnboardMsCommandType> supportedCommandMap;

	public OnboardMsGenericCommandHandler() {
		supportedCommandMap = new HashMap<>();
		supportedCommandMap.put(OnboardMsCommandType.InitiateOnboarding.getCommandName(),
				OnboardMsCommandType.InitiateOnboarding);
	}

	public OnboardMsEventProducer getOnboardMsEventProducer() {
		return onboardMsEventProducer;
	}

	public void setOnboardMsEventProducer(OnboardMsEventProducer onboardMsEventProducer) {
		this.onboardMsEventProducer = onboardMsEventProducer;
	}

	public OnboardMsAggregateLoader getOnboardMsAggregateLoader() {
		return onboardMsAggregateLoader;
	}

	public void setOnboardMsAggregateLoader(OnboardMsAggregateLoader onboardMsAggregateLoader) {
		this.onboardMsAggregateLoader = onboardMsAggregateLoader;
	}

	public OnboardMsAssociateService getOnboardMsAssociateService() {
		return onboardMsAssociateService;
	}

	public void setOnboardMsAssociateService(OnboardMsAssociateService onboardMsAssociateService) {
		this.onboardMsAssociateService = onboardMsAssociateService;
	}

	public void handle(String commandString) {

		System.out.println("Onboard MS : Command Handler : Receiving command " + commandString);
		try {

			OnboardMsGenericCommand command = OnboardMSJsonService.buildCommandFromJson(commandString);
			if (supportedCommandMap.get(command.getCommandName()) == null) {
				System.out.println("OnboardMS: CommandHandler: IgnoringCommand: " + command.getCommandName());
				return;
			}
			OnboardMsAssociateAggregate aggregate = onboardMsAggregateLoader.loadAggregate(command.getAggregateId());
			List<OnboardMsGenericEvent> eventList = aggregate.handleCommand(command, onboardMsAssociateService);
			for (OnboardMsGenericEvent currentEvent : eventList) {
				onboardMsEventProducer.raiseEvent(currentEvent);
			}

		} catch (Exception e) {
			System.out.println("Onboard MS Command Handler : Error in handling command : " + commandString);
			e.printStackTrace();
		}
	}

}
