package com.stpl.hr.onboard.domain.query.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiated;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMSDomainQueryHandlerTest {

	public static void main(String args[]) {
		Date todaysDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		OnboardMsGenericEvent event = new OnboardMsGenericEvent();
		event.setAggregateId(UUID.randomUUID().toString());
		event.setAggregateType("Associate");
		event.setEventName("onboardInitiated");
		event.setOriginAggregateId(event.getAggregateId());
		event.setRaisedTime(dateFormat.format(todaysDate));

		OnboardMsOnboardInitiated associateDataBean = new OnboardMsOnboardInitiated();
		associateDataBean.setAssociateId("DEV-2");
		associateDataBean.setFirstName("Sys");
		associateDataBean.setLastName("Biz");
		associateDataBean.setMobileNo("101");
		associateDataBean.setEmailId("ss@gmail.com");
		associateDataBean.setHighestDegree("Test");
		associateDataBean.setCgpa("Test");
		associateDataBean.setCollegeName("TEST");
		associateDataBean.setPreviousEmployer("Test");
		associateDataBean.setYearOfExperience("Test");
		associateDataBean.setBusinessUnit("VBU");
		event.setEventData(OnboardMSJsonService.convertObjectToJson(associateDataBean));

		System.out.println("Onboard Initiated Event Data:=== " + OnboardMSJsonService.convertObjectToJson(event));

	}

}
