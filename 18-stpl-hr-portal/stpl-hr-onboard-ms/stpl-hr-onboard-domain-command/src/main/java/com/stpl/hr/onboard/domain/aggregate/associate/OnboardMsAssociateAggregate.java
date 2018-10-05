package com.stpl.hr.onboard.domain.aggregate.associate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stpl.hr.onboard.domain.aggregate.OnboardMsAggregateType;
import com.stpl.hr.onboard.domain.aggregate.associate.designation.OnboardMsAssociateDesignation;
import com.stpl.hr.onboard.domain.aggregate.associate.role.OnboardMsAssociateRole;
import com.stpl.hr.onboard.domain.aggregate.associate.service.OnboardMsAssociateService;
import com.stpl.hr.onboard.domain.command.OnBoardMsInitiateOnboarding;
import com.stpl.hr.onboard.domain.command.OnboardMsCommandType;
import com.stpl.hr.onboard.domain.command.OnboardMsGenericCommand;
import com.stpl.hr.onboard.domain.event.OnboardMsEventType;
import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiated;
import com.stpl.hr.onboard.domain.event.OnboardMsOnboardInitiationFailed;
import com.stpl.hr.onboard.domain.service.OnboardMSJsonService;

public class OnboardMsAssociateAggregate {

	private String aggregateId;
	private String associateId;

	private String firstName;
	private String lastName;
	private String mobileNo;
	private String externalEmailId;

	private String highestDegree;
	private String cgpa;
	private String collegeName;

	private String previousEmployer;
	private float yearOfExperience;
	private String businessUnit;

	private OnboardMsAssociateRole role;
	private OnboardMsAssociateDesignation designation;
	private String joiningDate;

	public OnboardMsAssociateAggregate(String aggregateId) {
		super();
		this.aggregateId = aggregateId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getExternalEmailId() {
		return externalEmailId;
	}

	public void setExternalEmailId(String externalEmailId) {
		this.externalEmailId = externalEmailId;
	}

	public String getHighestDegree() {
		return highestDegree;
	}

	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}

	public String getCgpa() {
		return cgpa;
	}

	public void setCgpa(String cgpa) {
		this.cgpa = cgpa;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getPreviousEmployer() {
		return previousEmployer;
	}

	public void setPreviousEmployer(String previousEmployer) {
		this.previousEmployer = previousEmployer;
	}

	public float getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(float yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public OnboardMsAssociateRole getRole() {
		return role;
	}

	public void setRole(OnboardMsAssociateRole role) {
		this.role = role;
	}

	public OnboardMsAssociateDesignation getDesignation() {
		return designation;
	}

	public void setDesignation(OnboardMsAssociateDesignation designation) {
		this.designation = designation;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public void applyEvent(OnboardMsGenericEvent eventToBeApplied) throws IOException {

		this.aggregateId = eventToBeApplied.getAggregateId();

		if (eventToBeApplied.getEventName().equals(OnboardMsEventType.OnBoardInitiated.getEventName())) {

			OnboardMsOnboardInitiated onboardInitiated = (OnboardMsOnboardInitiated) OnboardMSJsonService
					.convertJsonToObject(OnboardMsOnboardInitiated.class, eventToBeApplied.getEventData());

			this.associateId = onboardInitiated.getAssociateId();
			this.firstName = onboardInitiated.getFirstName();
			this.lastName = onboardInitiated.getLastName();
			this.mobileNo = onboardInitiated.getMobileNo();
			this.externalEmailId = onboardInitiated.getEmailId();
			this.highestDegree = onboardInitiated.getHighestDegree();
			this.cgpa = onboardInitiated.getCgpa();
			this.collegeName = onboardInitiated.getCollegeName();
			this.previousEmployer = onboardInitiated.getPreviousEmployer();
			this.yearOfExperience = Float.parseFloat(onboardInitiated.getYearOfExperience());
			this.businessUnit = onboardInitiated.getBusinessUnit();
			this.role = OnboardMsAssociateRole.valueOf(onboardInitiated.getRole());
			this.designation = OnboardMsAssociateDesignation.valueOf(onboardInitiated.getDesignation());
			this.joiningDate = onboardInitiated.getJoiningDate();

			return;

		}

	}

	public List<OnboardMsGenericEvent> handleCommand(OnboardMsGenericCommand genericCommand,
			OnboardMsAssociateService onboardMsAssociateService) throws IOException {

		List<OnboardMsGenericEvent> eventList = new ArrayList<>();

		if (genericCommand.getCommandName().equals(OnboardMsCommandType.InitiateOnboarding.getCommandName())) {

			OnBoardMsInitiateOnboarding initiateOnboarding = (OnBoardMsInitiateOnboarding) OnboardMSJsonService
					.convertJsonToObject(OnBoardMsInitiateOnboarding.class, genericCommand.getCommandData());
			boolean canBeOnboarded = this.validateOnboardingDetails(initiateOnboarding);
			if (canBeOnboarded) {
				this.addOnboardingInitiatedEvent(eventList, genericCommand, initiateOnboarding,
						onboardMsAssociateService);
			} else {
				this.addOnboardingInitiationFailedEvent(eventList, genericCommand, initiateOnboarding);
			}

		}
		return eventList;

	}

	private void addOnboardingInitiationFailedEvent(List<OnboardMsGenericEvent> eventList,
			OnboardMsGenericCommand genericCommand, OnBoardMsInitiateOnboarding initiateOnboarding) {

		OnboardMsOnboardInitiationFailed initiationFailed = new OnboardMsOnboardInitiationFailed();
		initiationFailed.setFirstName(initiateOnboarding.getFirstName());
		initiationFailed.setLastName(initiateOnboarding.getLastName());
		initiationFailed.setAggregateId(genericCommand.getAggregateId());
		initiationFailed.setFailureReason("Onboard MS :Onboarding Failed. Designation "
				+ initiateOnboarding.getDesignation() + " is not applicable for "
				+ initiateOnboarding.getYearOfExperience() + " years of experience");
		OnboardMsGenericEvent genericEvent = buildGenericEvent(genericCommand,
				OnboardMsEventType.OnBoardInitiationFailed, initiationFailed);
		eventList.add(genericEvent);

	}

	private void addOnboardingInitiatedEvent(List<OnboardMsGenericEvent> eventList,
			OnboardMsGenericCommand genericCommand, OnBoardMsInitiateOnboarding initiateOnboarding,
			OnboardMsAssociateService onboardMsAssociateService) {

		OnboardMsOnboardInitiated onboardInitiatedEvent = new OnboardMsOnboardInitiated();
		this.firstName = initiateOnboarding.getFirstName();
		this.lastName = initiateOnboarding.getLastName();
		this.mobileNo = initiateOnboarding.getMobileNo();
		this.externalEmailId = initiateOnboarding.getEmailId();
		this.highestDegree = initiateOnboarding.getHighestDegree();
		this.cgpa = initiateOnboarding.getCgpa();
		this.collegeName = initiateOnboarding.getCollegeName();
		this.previousEmployer = initiateOnboarding.getPreviousEmployer();
		this.yearOfExperience = Float.parseFloat(initiateOnboarding.getYearOfExperience());
		this.businessUnit = initiateOnboarding.getBusinessUnit();

		this.joiningDate = initiateOnboarding.getJoiningDate();

		onboardInitiatedEvent.setFirstName(this.firstName);
		onboardInitiatedEvent.setLastName(this.lastName);
		onboardInitiatedEvent.setMobileNo(this.mobileNo);
		onboardInitiatedEvent.setEmailId(this.externalEmailId);
		onboardInitiatedEvent.setHighestDegree(this.highestDegree);
		onboardInitiatedEvent.setCgpa(this.cgpa);
		onboardInitiatedEvent.setCollegeName(this.getCollegeName());
		onboardInitiatedEvent.setPreviousEmployer(this.previousEmployer);
		onboardInitiatedEvent.setYearOfExperience(String.valueOf(this.yearOfExperience));
		onboardInitiatedEvent.setBusinessUnit(this.businessUnit);

		onboardInitiatedEvent.setJoiningDate(this.joiningDate);

		if (null != initiateOnboarding.getRole()) {
			this.role = OnboardMsAssociateRole.valueOf(initiateOnboarding.getRole());
			onboardInitiatedEvent.setRole(this.role.getRole());
		}
		if (null != initiateOnboarding.getDesignation()) {
			this.designation = OnboardMsAssociateDesignation.valueOf(initiateOnboarding.getDesignation());
			onboardInitiatedEvent.setDesignation(this.designation.getDesignation());
		}

		onboardInitiatedEvent.setAssociateId(onboardMsAssociateService.generateAssociateId());

		OnboardMsGenericEvent genericEvent = buildGenericEvent(genericCommand, OnboardMsEventType.OnBoardInitiated,
				onboardInitiatedEvent);
		eventList.add(genericEvent);

	}

	private OnboardMsGenericEvent buildGenericEvent(OnboardMsGenericCommand genericCommand,
			OnboardMsEventType eventType, Object eventObject) {
		OnboardMsGenericEvent genericEvent = new OnboardMsGenericEvent();
		genericEvent.setAggregateId(this.aggregateId);
		genericEvent.setAggregateType(OnboardMsAggregateType.AssociateAggregate.getAggregateType());
		genericEvent.setEventName(eventType.getEventName());
		genericEvent.setOriginAggregateId(genericCommand.getAggregateId());
		genericEvent.setOriginCommandRequestId(genericCommand.getOriginCommandRequestId());
		genericEvent.setOriginCommandName(genericCommand.getCommandName());
		genericEvent.setOriginIssuedTime(genericCommand.getIssuedTime());
		genericEvent.setEventData(OnboardMSJsonService.convertObjectToJson(eventObject));
		return genericEvent;
	}

	private boolean validateOnboardingDetails(OnBoardMsInitiateOnboarding initiateOnboarding) {
		boolean isDesignationValid = true;
		if (initiateOnboarding.getDesignation() != null) {
			isDesignationValid = OnboardMsAssociateDesignation.valueOf(initiateOnboarding.getDesignation())
					.validateDesignation(initiateOnboarding.getYearOfExperience());
		}
		return isDesignationValid;
	}
}
