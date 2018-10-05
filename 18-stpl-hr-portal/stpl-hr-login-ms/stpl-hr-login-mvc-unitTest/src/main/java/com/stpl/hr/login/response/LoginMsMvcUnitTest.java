package com.stpl.hr.login.response;

import com.stpl.hr.login.domain.command.LoginMsGenericCommand;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.LoginMsUserLoggedIn;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMsMvcUnitTest {
	public String getResponse(String msg) {
		String eventJson = "";
		try {
			LoginMsGenericCommand generiCommand = LoginMSJsonService.buildCommandFromJson(msg);
			LoginMsGenericEvent event = new LoginMsGenericEvent();
			event.setOriginCommandRequestId(generiCommand.getOriginCommandRequestId());

			LoginMsUserLoggedIn loggedInEvent = new LoginMsUserLoggedIn();
			loggedInEvent.setFirstName("rajadurai");
			loggedInEvent.setLastName("subramanian");
			loggedInEvent.setEmailId("rajadurai.s@sysbiz.com");
			loggedInEvent.setUserName("rajadurai.s");
			event.setEventData(LoginMSJsonService.convertObjectToJson(loggedInEvent));

			eventJson = LoginMSJsonService.convertObjectToJson(event);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventJson;
	}
}
