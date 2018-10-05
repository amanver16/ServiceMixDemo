package com.stpl.gtn.company.domain.query.handler;

import java.io.IOException;

import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdded;
import com.stpl.gtn.company.domain.event.CompanyMsEventType;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.query.mongo.service.CompanyMsQueryMongoService;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;

public class CompanyMsDomainQueryHandler {
	private CompanyMsQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(CompanyMsQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Company MS Domain Query Model : Receiving event " + content);
		CompanyMsGenericEvent event = CompanyMsJsonService.buildEventFromJson(content);
		if (CompanyMsEventType.CompanyAdded.getEventName().equals(event.getEventName())) {
			handleCompanyAddedEvent(event);
			return;
		}

		if (CompanyMsEventType.CompanyUpdated.getEventName().equals(event.getEventName())) {
			handleCompanyUpdatedEvent(event);
			return;
		}

		if (CompanyMsEventType.CompanyDeleted.getEventName().equals(event.getEventName())) {
			handleCompanyDeletedEvent(event);
			return;
		}

		System.out.println("Company MS Domain Query Model: Ignoring event " + event.getEventName());
	}

	public void handleCompanyAddedEvent(CompanyMsGenericEvent event) throws IOException {

		CompanyMsCompanyAdded company = (CompanyMsCompanyAdded) CompanyMsJsonService
				.convertJsonToObject(CompanyMsCompanyAdded.class, event.getEventData());

		company.getCompanyMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.insertCompany(company.getCompanyMasterBean());
	}

	public void handleCompanyUpdatedEvent(CompanyMsGenericEvent event) throws IOException {

		CompanyMsCompanyAdded company = (CompanyMsCompanyAdded) CompanyMsJsonService
				.convertJsonToObject(CompanyMsCompanyAdded.class, event.getEventData());

		company.getCompanyMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.updateCompany(company.getCompanyMasterBean());
	}

	public void handleCompanyDeletedEvent(CompanyMsGenericEvent event) throws IOException {

		CompanyMsCompanyAdded company = (CompanyMsCompanyAdded) CompanyMsJsonService
				.convertJsonToObject(CompanyMsCompanyAdded.class, event.getEventData());

		company.getCompanyMasterBean().setAggregateId(event.getAggregateId());
		domainQueryDbSevice.deleteCompany(company.getCompanyMasterBean());
	}
}
