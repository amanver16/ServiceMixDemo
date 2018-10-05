package com.stpl.hr.onboard.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stpl.hr.onboard.bus.consumer.OnboardMsEventTracker;
import com.stpl.hr.onboard.bus.gateway.OnboardMsQueryModelGateway;
import com.stpl.hr.onboard.bus.producer.OnboardMsCommandProducer;
import com.stpl.hr.onboard.domain.command.OnBoardMsInitiateOnboarding;
import com.stpl.hr.onboard.domain.command.OnboardMsCommandType;
import com.stpl.hr.onboard.domain.command.OnboardMsGenericCommand;
import com.stpl.hr.onboard.domain.event.OnboardMsEventType;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiated;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiationFailed;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;
import com.stpl.hr.onboard.mvc.request.OnboardMsInitiateOnboardingRequest;
import com.stpl.hr.onboard.mvc.response.OnboardMsInitiateOnboardingResponse;

@Path("/OnboardMs")
public class OnboardMsUserController {
	private OnboardMsCommandProducer onboardMsCommandProducer;

	private OnboardMsEventTracker onboardMsEventTracker;

	private OnboardMsQueryModelGateway onboardMsQueryModelGateway;

	public OnboardMsCommandProducer getOnboardMsCommandProducer() {
		return onboardMsCommandProducer;
	}

	public void setOnboardMsCommandProducer(OnboardMsCommandProducer onboardMsCommandProducer) {
		this.onboardMsCommandProducer = onboardMsCommandProducer;
	}

	public OnboardMsEventTracker getOnboardMsEventTracker() {
		return onboardMsEventTracker;
	}

	public void setOnboardMsEventTracker(OnboardMsEventTracker onboardMsEventTracker) {
		this.onboardMsEventTracker = onboardMsEventTracker;
	}

	public OnboardMsQueryModelGateway getOnboardMsQueryModelGateway() {
		return onboardMsQueryModelGateway;
	}

	public void setOnboardMsQueryModelGateway(OnboardMsQueryModelGateway onboardMsQueryModelGateway) {
		this.onboardMsQueryModelGateway = onboardMsQueryModelGateway;
	}

	@POST
	@Path("/onboardCandidate")
	@Produces(MediaType.APPLICATION_JSON)
	public String onboardCandidate(String userDetails) {
		OnboardMsInitiateOnboardingResponse httpResponse = new OnboardMsInitiateOnboardingResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");
		try {

			OnboardMsInitiateOnboardingRequest onboardUserRequest = (OnboardMsInitiateOnboardingRequest) OnboardMSJsonService
					.convertJsonToObject(OnboardMsInitiateOnboardingRequest.class, userDetails);
			OnboardMsGenericCommand genericCommand = buildOnboardInitiateCommand(onboardUserRequest);
			onboardMsCommandProducer.initiateCommand(genericCommand);
			onboardMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			OnboardMsGenericEvent eventResponse = onboardMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), 30);

			System.out.println("Onboard MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {

				buildInitiateOnboardingResponse(httpResponse, eventResponse);

			}
		} catch (IOException e) {
			httpResponse.setFailureReason("Onboard MS MVC :Http Exception while processing onboardCandidate");
			e.printStackTrace();
		}
		String convertedResponse = OnboardMSJsonService.convertObjectToJson(httpResponse);
		return convertedResponse;

	}

	@GET
	@Path("/getAllAssociate")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAssociate() {
		List<OnboardMsOnboardAssociateBean> associateList = onboardMsQueryModelGateway.getAllAssociate();
		String convertedResponse = OnboardMSJsonService.convertObjectToJson(associateList);
		System.out.println("OnboardMs : MVC Layer: getAllAssociate " + convertedResponse);
		return convertedResponse;

	}

	private void buildInitiateOnboardingResponse(OnboardMsInitiateOnboardingResponse httpResponse,
			OnboardMsGenericEvent eventResponse) throws IOException {
		httpResponse.setAggregateId(eventResponse.getAggregateId());
		httpResponse.setStatus("Failure");
		httpResponse.setFailureReason("Onboarding Failure");
		if (eventResponse.getEventName().equals(OnboardMsEventType.OnBoardInitiated.getEventName())) {
			OnboardMsOnboardInitiated onboardInitiatedEventData = (OnboardMsOnboardInitiated) OnboardMSJsonService
					.convertJsonToObject(OnboardMsOnboardInitiated.class, eventResponse.getEventData());
			httpResponse.setAssociateId(onboardInitiatedEventData.getAssociateId());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(OnboardMsEventType.OnBoardInitiationFailed.getEventName())) {
			OnboardMsOnboardInitiationFailed onboardInitiatedFailedEventData = (OnboardMsOnboardInitiationFailed) OnboardMSJsonService
					.convertJsonToObject(OnboardMsOnboardInitiationFailed.class, eventResponse.getEventData());
			httpResponse.setAggregateId(onboardInitiatedFailedEventData.getAggregateId());
			httpResponse.setFailureReason(onboardInitiatedFailedEventData.getFailureReason());
		}
		httpResponse.setAggregateId(httpResponse.getAggregateId());
	}

	private OnboardMsGenericCommand buildOnboardInitiateCommand(OnboardMsInitiateOnboardingRequest onboardUserRequest) {
		OnBoardMsInitiateOnboarding initiateOnboardingCommand = new OnBoardMsInitiateOnboarding();
		initiateOnboardingCommand.setBusinessUnit(onboardUserRequest.getBusinessUnit());
		initiateOnboardingCommand.setCgpa(onboardUserRequest.getCgpa());
		initiateOnboardingCommand.setCollegeName(onboardUserRequest.getCollegeName());
		initiateOnboardingCommand.setEmailId(onboardUserRequest.getEmailId());
		initiateOnboardingCommand.setFirstName(onboardUserRequest.getFirstName());
		initiateOnboardingCommand.setHighestDegree(onboardUserRequest.getHighestDegree());
		initiateOnboardingCommand.setLastName(onboardUserRequest.getLastName());
		initiateOnboardingCommand.setMobileNo(onboardUserRequest.getMobileNo());
		initiateOnboardingCommand.setPreviousEmployer(onboardUserRequest.getPreviousEmployer());
		initiateOnboardingCommand.setYearOfExperience(onboardUserRequest.getYearOfExperience());
		initiateOnboardingCommand.setRole(onboardUserRequest.getRole());
		initiateOnboardingCommand.setDesignation(onboardUserRequest.getDesignation());
		initiateOnboardingCommand.setJoiningDate(onboardUserRequest.getJoiningDate());
		String commandData = OnboardMSJsonService.convertObjectToJson(initiateOnboardingCommand);

		OnboardMsGenericCommand genericCommand = new OnboardMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(onboardMsQueryModelGateway.getAggregateIdByAssociateId(null));
		genericCommand.setCommandName(OnboardMsCommandType.InitiateOnboarding.getCommandName());
		return genericCommand;
	}

}
