package com.stpl.hr.login.querytest;

import java.util.UUID;

import com.stpl.hr.login.command.aggregate.LoginMsAggregateType;
import com.stpl.hr.login.common.service.LoginMsDateService;
import com.stpl.hr.login.domain.event.LoginMSNewUserRegsitered;
import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsOnboardInitiated;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMSUserQueryHandlerTest {

	public static void main(String args[]) {

		LoginMSUserQueryHandlerTest test = new LoginMSUserQueryHandlerTest();
		// System.out.println(test.generateNewUserRegisteredEvent());
		System.out.println(test.generateOnboardingInitiatedEvent());
	}

	public String produce() {
		System.out.println("Producer Send : ");
		String eventJson = generateNewUserRegisteredEvent();

		System.out.println("NewUserRegistered Event \n" + eventJson + "\n");
		return eventJson;
	}

	private String generateNewUserRegisteredEvent() {
		String eventJson = "";
		LoginMsGenericEvent event = new LoginMsGenericEvent();
		event.setAggregateId(UUID.randomUUID().toString());
		event.setEventName(LoginMsEventType.NewUserRegistered.getEventName());
		event.setAggregateType(LoginMsAggregateType.UserAggregate.getAggregateType());

		LoginMSNewUserRegsitered newUserRegistred = new LoginMSNewUserRegsitered();
		// newUserRegistred.setEmailId("santanu.m@sysbiz.com");
		// newUserRegistred.setFirstName("santanu");
		// newUserRegistred.setLastName("mohanty");
		// newUserRegistred.setPassword("welcome");
		// newUserRegistred.setUserName("santanu.mohanty");

		// newUserRegistred.setEmailId("rajadurai.s@sysbiz.com");
		// newUserRegistred.setFirstName("rajadurai");
		// newUserRegistred.setLastName("subramanian");
		// newUserRegistred.setPassword("welcome");
		// newUserRegistred.setUserName("rajadurai.subramanian");

		newUserRegistred.setEmailId("harlin.mani@sysbiz.com");
		newUserRegistred.setFirstName("Harlin");
		newUserRegistred.setLastName("Mani");
		newUserRegistred.setPassword("welcome");
		newUserRegistred.setUserName("harlin.mani");

		event.setEventData(LoginMSJsonService.convertObjectToJson(newUserRegistred));
		eventJson = LoginMSJsonService.convertObjectToJson(event);
		return eventJson;
	}

	private String generateOnboardingInitiatedEvent() {

		String eventJson = "";
		LoginMsGenericEvent event = new LoginMsGenericEvent();
		event.setAggregateId(UUID.randomUUID().toString());
		event.setEventName(LoginMsEventType.OnBoardInitiated.getEventName());
		event.setAggregateType(LoginMsAggregateType.UserAggregate.getAggregateType());
		event.setRaisedTime(LoginMsDateService.getCurrentDateInString());
		event.setOriginAggregateId(event.getAggregateId());

		LoginMsOnboardInitiated onboardingInitiated = new LoginMsOnboardInitiated();
		onboardingInitiated.setEmail("harlin.mani@sysbiz.com");
		onboardingInitiated.setFirstName("harlin");
		onboardingInitiated.setLastName("mani");

		event.setEventData(LoginMSJsonService.convertObjectToJson(onboardingInitiated));
		eventJson = LoginMSJsonService.convertObjectToJson(event);
		return eventJson;

	}

	public String getUserName(String userName) {
		return "LoginMsUserName";
	}

	public String getAggerateId(String aggregateId) {
		return "LoginMsAggerateId";
	}

}
