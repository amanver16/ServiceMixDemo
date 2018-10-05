package com.stpl.gtn.company.domain.aggregate.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stpl.gtn.company.domain.aggregate.CompanyMsAggregateType;
import com.stpl.gtn.company.domain.aggregate.company.service.CompanyMsService;
import com.stpl.gtn.company.domain.command.CompanyMsAddCompany;
import com.stpl.gtn.company.domain.command.CompanyMsCommandType;
import com.stpl.gtn.company.domain.command.CompanyMsDeleteCompany;
import com.stpl.gtn.company.domain.command.CompanyMsGenericCommand;
import com.stpl.gtn.company.domain.command.CompanyMsUpdateCompany;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdded;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdditionFailed;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyDeleted;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyDeletionFailed;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyUpdated;
import com.stpl.gtn.company.domain.event.CompanyMsEventType;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyInformationBean;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsCompanyAggregate {
	private String aggregateId;

	private CompanyMsCompanyBean companyBean;

	public CompanyMsCompanyAggregate(String aggregateId) {
		super();
		this.aggregateId = aggregateId;
	}

	public void applyEvent(CompanyMsGenericEvent eventToBeApplied) throws IOException {

		this.aggregateId = eventToBeApplied.getAggregateId();
		if (eventToBeApplied.getEventName().equals(CompanyMsEventType.CompanyAdded.getEventName())) {

			CompanyMsCompanyAdded companyAdded = (CompanyMsCompanyAdded) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyAdded.class, eventToBeApplied.getEventData());
			this.companyBean = companyAdded.getCompanyMasterBean();
			return;

		}

		return;

	}

	public List<CompanyMsGenericEvent> handleCommand(CompanyMsGenericCommand genericCommand,
			CompanyMsService companyMsService) throws IOException {

		List<CompanyMsGenericEvent> eventList = new ArrayList<>();

		if (genericCommand.getCommandName().equals(CompanyMsCommandType.AddCompany.getCommandName())) {

			CompanyMsAddCompany addCompany = (CompanyMsAddCompany) CompanyMsJsonService
					.convertJsonToObject(CompanyMsAddCompany.class, genericCommand.getCommandData());
			CompanyMsCompanyBean companyMasterBean = addCompany.getCompanyMasterBean();
			CompanyMsCompanyInformationBean companyInformationBean = companyMasterBean.getCompanyInformationBean();

			if ((companyInformationBean.getCompanyId() == null) || (companyInformationBean.getCompanyNo() == null)
					|| (companyInformationBean.getCompanyName() == null)) {
				System.out.println("Company MS : Domain Command : company details not present");
				addCompanyAddingFailedEvent(eventList, genericCommand, addCompany);
			} else {
				addCompanyAddedEvent(eventList, genericCommand, addCompany, companyMsService);
			}

		}
		if (genericCommand.getCommandName().equals(CompanyMsCommandType.UpdateCompany.getCommandName())) {

			CompanyMsUpdateCompany updateCompany = (CompanyMsUpdateCompany) CompanyMsJsonService
					.convertJsonToObject(CompanyMsUpdateCompany.class, genericCommand.getCommandData());
			CompanyMsCompanyBean companyMasterBean = updateCompany.getCompanyMasterBean();

			if (companyMasterBean.getAggregateId() == null) {
				System.out.println("Company MS : Domain Command : aggregateId not present for update.");
				addCompanyUpdatingFailedEvent(eventList, genericCommand, updateCompany);
			} else {
				this.companyBean = updateCompany.getCompanyMasterBean();
				addCompanyUpdatedEvent(eventList, genericCommand, updateCompany, companyMsService);
			}

		}
		if (genericCommand.getCommandName().equals(CompanyMsCommandType.DeleteCompany.getCommandName())) {

			CompanyMsDeleteCompany deleteCompany = (CompanyMsDeleteCompany) CompanyMsJsonService
					.convertJsonToObject(CompanyMsDeleteCompany.class, genericCommand.getCommandData());
			CompanyMsCompanyBean companyMasterBean = deleteCompany.getCompanyMasterBean();

			if (companyMasterBean.getAggregateId() == null) {
				System.out.println("Company MS : Domain Command : aggregateId not present for delete.");
				addCompanyDeletingFailedEvent(eventList, genericCommand, deleteCompany);
			} else {
				this.companyBean = deleteCompany.getCompanyMasterBean();
				addCompanyDeletedEvent(eventList, genericCommand, deleteCompany, companyMsService);
			}

		}

		return eventList;

	}

	private void addCompanyAddedEvent(List<CompanyMsGenericEvent> eventList, CompanyMsGenericCommand genericCommand,
			CompanyMsAddCompany addCompanyToInventory, CompanyMsService companyMsService) {

		CompanyMsCompanyAdded companyAddedEvent = new CompanyMsCompanyAdded();
		companyAddedEvent.setCompanyMasterBean(addCompanyToInventory.getCompanyMasterBean());

		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyAdded,
				companyAddedEvent);
		eventList.add(genericEvent);

	}

	private void addCompanyAddingFailedEvent(List<CompanyMsGenericEvent> eventList,
			CompanyMsGenericCommand genericCommand, CompanyMsAddCompany addCompanyToInventory) {

		CompanyMsCompanyAdditionFailed companyAddingFailed = new CompanyMsCompanyAdditionFailed();

		companyAddingFailed.setFailureReason("Company  is not valid");
		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyAdditionFailed,
				companyAddingFailed);
		eventList.add(genericEvent);

	}

	private CompanyMsGenericEvent buildGenericEvent(CompanyMsGenericCommand genericCommand,
			CompanyMsEventType eventType, Object eventObject) {
		CompanyMsGenericEvent genericEvent = new CompanyMsGenericEvent();
		genericEvent.setAggregateId(this.aggregateId);
		genericEvent.setAggregateType(CompanyMsAggregateType.CompanyAggregate.getAggregateType());
		genericEvent.setEventName(eventType.getEventName());
		genericEvent.setOriginAggregateId(genericCommand.getAggregateId());
		genericEvent.setOriginCommandRequestId(genericCommand.getOriginCommandRequestId());
		genericEvent.setOriginCommandName(genericCommand.getCommandName());
		genericEvent.setOriginIssuedTime(genericCommand.getIssuedTime());
		genericEvent.setEventData(CompanyMsJsonService.convertObjectToJson(eventObject));
		return genericEvent;
	}

	private void addCompanyUpdatingFailedEvent(List<CompanyMsGenericEvent> eventList,
			CompanyMsGenericCommand genericCommand, CompanyMsUpdateCompany updateCompany) {
		CompanyMsCompanyAdditionFailed companyUpdatingFailed = new CompanyMsCompanyAdditionFailed();

		companyUpdatingFailed.setFailureReason("Company aggregateId is not present for update.");
		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyUpdationFailed,
				companyUpdatingFailed);
		eventList.add(genericEvent);
	}

	private void addCompanyUpdatedEvent(List<CompanyMsGenericEvent> eventList, CompanyMsGenericCommand genericCommand,
			CompanyMsUpdateCompany updateCompany, CompanyMsService companyMsService) {

		CompanyMsCompanyUpdated companyUpdatedEvent = new CompanyMsCompanyUpdated();
		companyUpdatedEvent.setCompanyMasterBean(updateCompany.getCompanyMasterBean());
		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyUpdated,
				companyUpdatedEvent);
		eventList.add(genericEvent);

	}

	private void addCompanyDeletedEvent(List<CompanyMsGenericEvent> eventList, CompanyMsGenericCommand genericCommand,
			CompanyMsDeleteCompany deleteCompany, CompanyMsService companyMsService) {
		CompanyMsCompanyDeleted companyDeletedEvent = new CompanyMsCompanyDeleted();
		companyDeletedEvent.setCompanyMasterBean(deleteCompany.getCompanyMasterBean());
		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyDeleted,
				companyDeletedEvent);
		eventList.add(genericEvent);
	}

	private void addCompanyDeletingFailedEvent(List<CompanyMsGenericEvent> eventList,
			CompanyMsGenericCommand genericCommand, CompanyMsDeleteCompany deleteCompany) {
		CompanyMsCompanyDeletionFailed companyDeletingFailed = new CompanyMsCompanyDeletionFailed();

		companyDeletingFailed.setFailureReason("Company aggregateId is not present for delete.");
		CompanyMsGenericEvent genericEvent = buildGenericEvent(genericCommand, CompanyMsEventType.CompanyDeletionFailed,
				companyDeletingFailed);
		eventList.add(genericEvent);
	}
}
