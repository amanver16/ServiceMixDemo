package com.stpl.hr.login.domain.query.handler;

import java.io.IOException;

import com.stpl.hr.login.domain.event.LoginMSNewUserRegsitered;
import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.query.data.bean.LoginMsUserQueryBean;
import com.stpl.hr.login.domain.query.mongo.service.LoginMSDomainQueryMongoService;
import com.stpl.hr.login.domain.service.LoginMSJsonService;

public class LoginMSUserQueryHandler {

	private LoginMSDomainQueryMongoService loginMsUserQueryDbService;

	public LoginMSUserQueryHandler() {
		super();
	}

	public void handleEvent(String content) throws IOException {
		LoginMsGenericEvent event = LoginMSJsonService.buildEventFromJson(content);
		System.out.println("LoginMs : Read Model: Receiving Event " + content);

		if (LoginMsEventType.NewUserRegistered.getEventName().equals(event.getEventName())) {

			handleNewUserRegisteredEvent(event);
			return;
		}

		System.out.println("LoginMs : Read Model: Ignoring event " + event.getEventName());
		return;

	}

	private void handleNewUserRegisteredEvent(LoginMsGenericEvent event) {

		try {
			LoginMSNewUserRegsitered eventObj = (LoginMSNewUserRegsitered) LoginMSJsonService
					.convertJsonToObject(LoginMSNewUserRegsitered.class, event.getEventData());
			LoginMsUserQueryBean queryBean = new LoginMsUserQueryBean();
			queryBean.setAggregateId(event.getAggregateId());
			queryBean.setUserName(eventObj.getUserName());
			loginMsUserQueryDbService.addUser(queryBean);
			System.out.println("LoginMs : Read Model : Stored newUser " + queryBean.getUserName());

		} catch (Exception e) {
			System.out.println("LoginMs : Error In add User");
			e.printStackTrace();
		}
		return;
	}

	public LoginMSDomainQueryMongoService getLoginMsUserQueryDbService() {
		return loginMsUserQueryDbService;
	}

	public void setLoginMsUserQueryDbService(LoginMSDomainQueryMongoService loginMsUserQueryDbService) {
		this.loginMsUserQueryDbService = loginMsUserQueryDbService;
	}

}
