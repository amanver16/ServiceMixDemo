package com.stpl.hr.login.domain.aggregate.processmanager;

import java.io.IOException;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.login.common.service.LoginMsDateService;
import com.stpl.hr.login.domain.command.LoginMsCommandType;
import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.command.LoginMsRegisterNewUser;
import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsOnboardInitiated;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMsUserProcessManager {

	@Produce(uri = "jms:topic:loginMsCommandBus")
	ProducerTemplate loginMsCommandProducer;

	public void handleEvent(String content) throws IOException {
		System.out.println("LoginMs : Command : UserProcessManager : Receiving event " + content);
		LoginMsGenericEvent event = LoginMSJsonService.buildEventFromJson(content);
		try {

			if (event.getEventName().equals(LoginMsEventType.OnBoardInitiated.getEventName())) {

				LoginMsGenericCommand genericCommand = new LoginMsGenericCommand();
				genericCommand.setAggregateId(event.getAggregateId());
				genericCommand.setCommandName(LoginMsCommandType.RegisterNewUser.getCommandName());
				genericCommand.setOriginEventRaisedTime(event.getRaisedTime());
				genericCommand.setOriginEventAggregateId(event.getOriginAggregateId());
				genericCommand.setOriginEventName(event.getEventName());

				LoginMsOnboardInitiated onboardInitiated = (LoginMsOnboardInitiated) LoginMSJsonService
						.convertJsonToObject(LoginMsOnboardInitiated.class, event.getEventData());
				LoginMsRegisterNewUser registerNewUser = new LoginMsRegisterNewUser();
				registerNewUser.setFirstName(onboardInitiated.getFirstName());
				registerNewUser.setLastName(onboardInitiated.getLastName());
				registerNewUser.setEmail(onboardInitiated.getEmailId());
				genericCommand.setCommandData(LoginMSJsonService.convertObjectToJson(registerNewUser));
				genericCommand.setIssuedTime(LoginMsDateService.getCurrentDateInString());

				String commandJson = LoginMSJsonService.convertObjectToJson(genericCommand);
				System.out.println("LoginMs : Command : UserProcessManager : Issuing command with commandName "
						+ LoginMsCommandType.RegisterNewUser.getCommandName() + " commandJson " + commandJson);
				loginMsCommandProducer.sendBody(commandJson);

			} else {
				System.out.println("LoginMs : Command : UserProcessManager : Ignoring event " + event.getEventName());
			}

		} catch (Exception e) {
			System.out.println("LoginMs : Command : UserProcessManager : Error in storing event ");
			e.printStackTrace();
		}
	}
}
