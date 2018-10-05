package com.stpl.hr.onboard.domain.query.handler;

import java.io.IOException;

import com.stpl.hr.onboard.domain.event.OnboardMsEventType;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiated;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMSAssetAllocated;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMSNewUserRegistered;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;
import com.stpl.hr.onboard.domain.query.mongo.service.OnboardMSDomainQueryMongoService;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMSDomainQueryHandler {

	public OnboardMSDomainQueryHandler() {
		super();
	}

	private OnboardMSDomainQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(OnboardMSDomainQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Onboard MS Domain Query Model : Receiving event " + content);
		OnboardMsGenericEvent event = OnboardMSJsonService.buildEventFromJson(content);
		if (OnboardMsEventType.OnBoardInitiated.getEventName().equals(event.getEventName())) {
			handleOnboardInitiatedEvent(event);
			return;
		}

		if (OnboardMsEventType.NewUserRegistered.getEventName().equals(event.getEventName())) {
			handleNewUserRegisteredEvent(event);
			return;
		}

		if (OnboardMsEventType.AssetAllocated.getEventName().equals(event.getEventName())) {
			handleAssetAllocated(event);
			return;
		}
		System.out.println("Onboard MS Domain Query Model: Ignoring event " + event.getEventName());
		return;

	}

	private void handleOnboardInitiatedEvent(OnboardMsGenericEvent event) {
		try {
			OnboardMsOnboardInitiated eventObj = (OnboardMsOnboardInitiated) OnboardMSJsonService
					.convertJsonToObject(OnboardMsOnboardInitiated.class, event.getEventData());
			OnboardMsOnboardAssociateBean associateDataBean = new OnboardMsOnboardAssociateBean();
			associateDataBean.setAssociateId(eventObj.getAssociateId());
			associateDataBean.setAggregateId(event.getAggregateId());
			associateDataBean.setFirstName(eventObj.getFirstName());
			associateDataBean.setLastName(eventObj.getLastName());
			associateDataBean.setMobileNo(eventObj.getMobileNo());
			associateDataBean.setEmailId(eventObj.getEmailId());
			associateDataBean.setHighestDegree(eventObj.getHighestDegree());
			associateDataBean.setCgpa(eventObj.getCgpa());
			associateDataBean.setCollegeName(eventObj.getCollegeName());
			associateDataBean.setPreviousEmployer(eventObj.getPreviousEmployer());
			associateDataBean.setYearOfExperience(eventObj.getYearOfExperience());
			associateDataBean.setBusinessUnit(eventObj.getBusinessUnit());
			associateDataBean.setRole(eventObj.getRole());
			associateDataBean.setDesignation(eventObj.getDesignation());
			associateDataBean.setJoiningDate(eventObj.getJoiningDate());
			associateDataBean.setStatus(event.getEventName());
			domainQueryDbSevice.onboardAssociate(associateDataBean);
			System.out.println(
					"Onboard MS Domain Query Model : Stored new Associate " + associateDataBean.getFirstName());

		} catch (Exception e) {
			System.out.println("OnboardMS : Error In onboard accociate");
			e.printStackTrace();
		}
		return;
	}

	private void handleNewUserRegisteredEvent(OnboardMsGenericEvent event) {

		try {
			OnboardMSNewUserRegistered eventObj = (OnboardMSNewUserRegistered) OnboardMSJsonService
					.convertJsonToObject(OnboardMSNewUserRegistered.class, event.getEventData());
			OnboardMsOnboardAssociateBean associateDataBean = domainQueryDbSevice.getAssociateDetailsByAggregateId(
					event.getAggregateId(), OnboardMsEventType.OnBoardInitiated.getEventName());
			associateDataBean.setUserName(eventObj.getUserName());
			associateDataBean.setStatus(event.getEventName());
			domainQueryDbSevice.onboardAssociate(associateDataBean);
			System.out.println("OnboardMS : Read Model : NewUserRegisteredEvent " + associateDataBean.getUserName());

		} catch (Exception e) {
			System.out.println("OnboardMS : Read Model : Error NewUserRegisteredEvent");
			e.printStackTrace();
		}
		return;
	}

	private void handleAssetAllocated(OnboardMsGenericEvent event) {
		System.out.println("OnboardMS : Read Model : Event : " + event);
		try {
			OnboardMSAssetAllocated eventObj = (OnboardMSAssetAllocated) OnboardMSJsonService
					.convertJsonToObject(OnboardMSAssetAllocated.class, event.getEventData());
			OnboardMsOnboardAssociateBean associateDataBean = domainQueryDbSevice
					.getAssociateDetails(eventObj.getAssetOwnerId());
			System.out.println(
					"OnboardMS : Read Model : OnboardMsOnboardAssociateBean associateDataBean : " + associateDataBean);
			associateDataBean.setStatus(event.getEventName());
			associateDataBean.setAssetInfo("DESKTOP " + eventObj.getAssetId());
			domainQueryDbSevice.onboardAssociate(associateDataBean);
			System.out.println("OnboardMS : Read Model : AssetAllocatedEvent " + associateDataBean.getAssetInfo());

		} catch (Exception e) {
			System.out.println("OnboardMS : Read Model : Error AssetAllocatedEvent");
			e.printStackTrace();
		}
		return;
	}

}
