package com.stpl.hr.login.domainevent.store.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.stpl.hr.login.domain.event.LoginMsEventType;
import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.service.LoginMSJsonService;
import com.stpl.hr.login.domainevent.store.mongo.service.LoginMSDomainEventStoreMongoService;

public class LoginMsEventStoreHandler {
	private Map<String, LoginMsEventType> supportedEventMap;

	public LoginMsEventStoreHandler() {
		super();
		supportedEventMap = new HashMap<>();
		supportedEventMap.put(LoginMsEventType.NewUserRegistered.getEventName(), LoginMsEventType.NewUserRegistered);
		supportedEventMap.put(LoginMsEventType.OnBoardInitiated.getEventName(), LoginMsEventType.OnBoardInitiated);
		supportedEventMap.put(LoginMsEventType.PasswordUpdated.getEventName(), LoginMsEventType.PasswordUpdated);
		supportedEventMap.put(LoginMsEventType.PasswordUpdateFailed.getEventName(),
				LoginMsEventType.PasswordUpdateFailed);
		supportedEventMap.put(LoginMsEventType.UserLoggedIn.getEventName(), LoginMsEventType.UserLoggedIn);
		supportedEventMap.put(LoginMsEventType.UserLoggedInFailed.getEventName(), LoginMsEventType.UserLoggedInFailed);
	}

	LoginMSDomainEventStoreMongoService loginMsEventStoreDbService;

	public void setLoginMsEventStoreDbService(LoginMSDomainEventStoreMongoService loginMsEventStoreDbService) {
		this.loginMsEventStoreDbService = loginMsEventStoreDbService;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("LoginMs : Event Store-Write Model : Receiving event " + content);
		LoginMsGenericEvent event = LoginMSJsonService.buildEventFromJson(content);
		try {
			if (supportedEventMap.get(event.getEventName()) == null) {
				System.out.println("LoginMs: Event Store-Write Model : Ignoring event: " + event.getEventName());
				return;
			}
			int duplicateCount = loginMsEventStoreDbService.checkForDuplicateAggregate(event.getAggregateId());
			if (duplicateCount == 0) {
				loginMsEventStoreDbService.addLoginAggregate(event);
			}
			loginMsEventStoreDbService.addLoginAggregateEvent(event, content);
			System.out.println("LoginMs : Event Store-Write Model : event stored " + content);

		} catch (SQLException e) {
			System.out.println("LoginMs : Event Store-Write Model : Error in storing event ");
			e.printStackTrace();
		}
	}

}
