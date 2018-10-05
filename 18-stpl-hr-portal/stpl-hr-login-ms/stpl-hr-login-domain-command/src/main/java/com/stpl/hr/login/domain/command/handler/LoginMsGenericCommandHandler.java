package com.stpl.hr.login.domain.command.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.stpl.hr.login.bus.event.producer.LoginMsEventProducer;
import com.stpl.hr.login.domain.aggregate.LoginMsUserAggregate;
import com.stpl.hr.login.domain.aggregate.loader.LoginMsAggregateLoader;
import com.stpl.hr.login.domain.command.LoginMsCommandType;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

@Component
public class LoginMsGenericCommandHandler {

	private LoginMsEventProducer loginMsEventProducer;

	private LoginMsAggregateLoader loginMsAggregateLoader;

	private Map<String, LoginMsCommandType> supportedCommandMap;

	public LoginMsGenericCommandHandler() {
		supportedCommandMap = new HashMap<>();
		supportedCommandMap.put(LoginMsCommandType.CheckUserCredentials.getCommandName(),
				LoginMsCommandType.CheckUserCredentials);
		supportedCommandMap.put(LoginMsCommandType.RegisterNewUser.getCommandName(),
				LoginMsCommandType.RegisterNewUser);
		supportedCommandMap.put(LoginMsCommandType.UpdatePassword.getCommandName(), LoginMsCommandType.UpdatePassword);
	}

	public LoginMsEventProducer getLoginMsEventProducer() {
		return loginMsEventProducer;
	}

	public void setLoginMsEventProducer(LoginMsEventProducer loginMsEventProducer) {
		this.loginMsEventProducer = loginMsEventProducer;
	}

	public LoginMsAggregateLoader getLoginMsAggregateLoader() {
		return loginMsAggregateLoader;
	}

	public void setLoginMsAggregateLoader(LoginMsAggregateLoader loginMsAggregateLoader) {
		this.loginMsAggregateLoader = loginMsAggregateLoader;
	}

	public void handle(String commandString) {

		System.out.println("LoginMs : Command : Command Handler : Receiving command " + commandString);
		try {

			LoginMsGenericCommand command = LoginMSJsonService.buildCommandFromJson(commandString);

			if (supportedCommandMap.get(command.getCommandName()) == null) {
				System.out.println("LoginMs: CommandHandler: IgnoringCommand: " + command.getCommandName());
				return;
			}

			LoginMsUserAggregate aggregate = loginMsAggregateLoader.loadAggregate(command.getAggregateId());
			List<LoginMsGenericEvent> eventList = aggregate.handleCommand(command);
			for (LoginMsGenericEvent currentEvent : eventList) {
				loginMsEventProducer.raiseEvent(currentEvent);
			}

		} catch (Exception e) {
			System.out.println("LoginMs : Command : Command Handler : Error in handling command : " + commandString);
			e.printStackTrace();
		}
	}

}
