package com.stpl.gtn.company.mvc.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stpl.gtn.company.domain.command.CompanyMsAddCompany;
import com.stpl.gtn.company.domain.command.CompanyMsCommandType;
import com.stpl.gtn.company.domain.command.CompanyMsDeleteCompany;
import com.stpl.gtn.company.domain.command.CompanyMsGenericCommand;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdded;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyAdditionFailed;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyDeleted;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyDeletionFailed;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyUpdated;
import com.stpl.gtn.company.domain.event.CompanyMsCompanyUpdationFailed;
import com.stpl.gtn.company.domain.event.CompanyMsEventType;
import com.stpl.gtn.company.domain.event.CompanyMsGenericEvent;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyInformationBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchInputBean;
import com.stpl.gtn.company.domain.service.CompanyMsJsonService;
import com.stpl.gtn.company.mvc.bus.consumer.CompanyMsEventTracker;
import com.stpl.gtn.company.mvc.bus.gateway.CompanyMsQueryModelGateway;
import com.stpl.gtn.company.mvc.bus.producer.CompanyMsCommandProducer;
import com.stpl.gtn.company.mvc.request.CompanyMsCompanyInventoryRequest;
import com.stpl.gtn.company.mvc.request.GtnFrameworkWebserviceRequest;
import com.stpl.gtn.company.mvc.request.GtnWsCompanyDeleteRequest;
import com.stpl.gtn.company.mvc.request.GtnWsSearchRequest;
import com.stpl.gtn.company.mvc.response.CompanyMsCompanyInventoryResponse;
import com.stpl.gtn.company.mvc.response.GtnFrameworkWebserviceResponse;

public class CompanyMsCompanyController {

	public CompanyMsCompanyController() {
		super();
	}

	private CompanyMsCommandProducer companyMsCommandProducer;
	private CompanyMsEventTracker companyMsEventTracker;
	private CompanyMsQueryModelGateway companyMsQueryModelGateway;

	public void setCompanyMsCommandProducer(CompanyMsCommandProducer companyMsCommandProducer) {
		this.companyMsCommandProducer = companyMsCommandProducer;
	}

	public void setCompanyMsEventTracker(CompanyMsEventTracker companyMsEventTracker) {
		this.companyMsEventTracker = companyMsEventTracker;
	}

	public void setCompanyMsQueryModelGateway(CompanyMsQueryModelGateway companyMsQueryModelGateway) {
		this.companyMsQueryModelGateway = companyMsQueryModelGateway;
	}

	@POST
	@Path("/GtnWsCMSaveService")
	@Produces(MediaType.APPLICATION_JSON)
	public String addCompany(String companyDetails) {
		GtnFrameworkWebserviceResponse wsResponse = new GtnFrameworkWebserviceResponse();

		wsResponse.setStatus("Exception");
		wsResponse.setFailureReason("Event not received");
		try {
			GtnFrameworkWebserviceRequest webserviceRequest = (GtnFrameworkWebserviceRequest) CompanyMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, companyDetails);

			CompanyMsCompanyInventoryRequest companyInventoryRequest = webserviceRequest.getCompanyInventoryRequest();
			CompanyMsCompanyBean companyBean = companyInventoryRequest.getCompanyBean();
			CompanyMsCompanyInformationBean companyInfoBean = companyBean.getCompanyInformationBean();
			if (companyMsQueryModelGateway.isCompanyIdDuplicate(companyInfoBean.getCompanyId())) {

				wsResponse.setStatus("Failure");
				wsResponse
						.setFailureReason("Company Serial Id " + companyInfoBean.getCompanyId() + " is already added");
				String convertedResponse = CompanyMsJsonService.convertObjectToJson(wsResponse);
				System.out.println("Company MS MVC Layer: Without triggering command and event Response to UI "
						+ convertedResponse);
				return convertedResponse;

			}
			CompanyMsGenericCommand genericCommand = buildCompanyInventoryCommand(companyInventoryRequest);
			companyMsCommandProducer.initiateCommand(genericCommand);
			companyMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			CompanyMsGenericEvent eventResponse = companyMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), 30);

			System.out.println("Company MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {
				buildCompanyMsResponse(wsResponse, eventResponse);
			}
		} catch (IOException e) {
			wsResponse.setFailureReason("Company MS MVC :Http Exception while processing addCompany");
			e.printStackTrace();
		}
		String convertedResponse = CompanyMsJsonService.convertObjectToJson(wsResponse);
		return convertedResponse;

	}

	private CompanyMsGenericCommand buildCompanyInventoryCommand(
			CompanyMsCompanyInventoryRequest companyMsInventorytRequest) {
		CompanyMsAddCompany initiateCmToInventoryCommand = new CompanyMsAddCompany();
		CompanyMsCompanyBean cmBean = companyMsInventorytRequest.getCompanyBean();
		initiateCmToInventoryCommand.setCompanyMasterBean(cmBean);

		String commandData = CompanyMsJsonService.convertObjectToJson(initiateCmToInventoryCommand);

		CompanyMsGenericCommand genericCommand = new CompanyMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(companyMsQueryModelGateway.getCompanyAggregateId());
		genericCommand.setCommandName(CompanyMsCommandType.AddCompany.getCommandName());
		if (cmBean.getAggregateId() != null) {
			genericCommand.setAggregateId(cmBean.getAggregateId());
			genericCommand.setCommandName(CompanyMsCommandType.UpdateCompany.getCommandName());
		}
		return genericCommand;
	}

	private void buildCompanyMsResponse(GtnFrameworkWebserviceResponse httpResponse,
			CompanyMsGenericEvent eventResponse) throws IOException {
		CompanyMsCompanyInventoryResponse companyInventoryResponse = new CompanyMsCompanyInventoryResponse();
		httpResponse.setStatus("Failure");
		httpResponse.setFailureReason("Company Addition Failure");
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyAdded.getEventName())) {
			CompanyMsCompanyAdded companyAddedEventData = (CompanyMsCompanyAdded) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyAdded.class, eventResponse.getEventData());
			companyInventoryResponse.setCompanyBean(companyAddedEventData.getCompanyMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyAdditionFailed.getEventName())) {
			CompanyMsCompanyAdditionFailed companyAdditionFailedEventData = (CompanyMsCompanyAdditionFailed) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyAdditionFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(companyAdditionFailedEventData.getFailureReason());
		}
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyUpdated.getEventName())) {
			CompanyMsCompanyUpdated companyUpdatedEventData = (CompanyMsCompanyUpdated) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyUpdated.class, eventResponse.getEventData());
			companyInventoryResponse.setCompanyBean(companyUpdatedEventData.getCompanyMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyUpdationFailed.getEventName())) {
			CompanyMsCompanyUpdationFailed companyUpdationFailedEventData = (CompanyMsCompanyUpdationFailed) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyUpdationFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(companyUpdationFailedEventData.getFailureReason());
		}
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyDeleted.getEventName())) {
			CompanyMsCompanyDeleted companyDeletedEventData = (CompanyMsCompanyDeleted) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyDeleted.class, eventResponse.getEventData());
			companyInventoryResponse.setCompanyBean(companyDeletedEventData.getCompanyMasterBean());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
			httpResponse.setSucess(true);
		}
		if (eventResponse.getEventName().equals(CompanyMsEventType.CompanyDeletionFailed.getEventName())) {
			CompanyMsCompanyDeletionFailed companyDeletionFailedEventData = (CompanyMsCompanyDeletionFailed) CompanyMsJsonService
					.convertJsonToObject(CompanyMsCompanyDeletionFailed.class, eventResponse.getEventData());
			httpResponse.setFailureReason(companyDeletionFailedEventData.getFailureReason());
		}
		httpResponse.setCompanyInventoryResponse(companyInventoryResponse);
	}

	@POST
	@Path("/CompanyMsCompanySearchService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompanies(String searchCriteria) {
		GtnFrameworkWebserviceResponse wsResponse = new GtnFrameworkWebserviceResponse();
		wsResponse.setStatus("Exception");
		wsResponse.setFailureReason("Event not received");
		System.out.println("CompanyMS : MVC Layer: Inside getCompanies ");
		try {
			GtnFrameworkWebserviceRequest wsRequest = (GtnFrameworkWebserviceRequest) CompanyMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, searchCriteria);
			GtnWsSearchRequest gtnWsSearchRequest = wsRequest.getGtnWsSearchRequest();

			CompanyMsSearchInputBean searchInputBean = new CompanyMsSearchInputBean();
			searchInputBean.setSearchCriteriaList(gtnWsSearchRequest.getSearchCriteriaList());
			searchInputBean.setOffset(gtnWsSearchRequest.getTableRecordOffset());
			searchInputBean.setStart(gtnWsSearchRequest.getTableRecordStart());
			String searchCriteriaList = CompanyMsJsonService.convertObjectToJson(searchInputBean);
			List<CompanyMsSearchBean> associateList = companyMsQueryModelGateway.getCompanies(searchCriteriaList);
			wsResponse.addCompanyBeanToSearchResponse(associateList);
			wsResponse.setStatus("Success");
			wsResponse.setFailureReason("");
			wsResponse.setSucess(true);
		} catch (IOException e) {
			wsResponse.setSucess(false);
			wsResponse.setFailureReason("Company MS MVC :Http Exception while processing getCompanies");
			e.printStackTrace();
		}
		String convertedResponse = CompanyMsJsonService.convertObjectToJson(wsResponse);
		System.out.println("ComapnyMS : MVC Layer: getCompanies " + convertedResponse);
		return convertedResponse;
	}

	@POST
	@Path("/CompanyMsCompanyDeleteService")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCompany(String companyBean) {
		GtnFrameworkWebserviceResponse httpResponse = new GtnFrameworkWebserviceResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");
		System.out.println("CompanyMS : MVC Layer: Inside deleteCompany ");
		try {
			GtnFrameworkWebserviceRequest wsRequest = (GtnFrameworkWebserviceRequest) CompanyMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, companyBean);
			GtnWsCompanyDeleteRequest wsCompanyDeleteRequest = wsRequest.getWsCompanyDeleteRequest();

			CompanyMsGenericCommand genericCommand = buildDeleteCompanyCommand(wsCompanyDeleteRequest);
			companyMsCommandProducer.initiateCommand(genericCommand);
			companyMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			CompanyMsGenericEvent eventResponse = companyMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), 30);

			System.out.println("Company MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {
				buildCompanyMsResponse(httpResponse, eventResponse);
			}
		} catch (IOException e) {
			httpResponse.setSucess(false);
			httpResponse.setFailureReason("Company MS MVC :Http Exception while processing delete company");
			e.printStackTrace();
		}
		String convertedResponse = CompanyMsJsonService.convertObjectToJson(httpResponse);
		System.out.println("ComapnyMS : MVC Layer: getCompanies " + convertedResponse);
		return convertedResponse;
	}

	private CompanyMsGenericCommand buildDeleteCompanyCommand(GtnWsCompanyDeleteRequest wsCompanyDeleteRequest) {
		CompanyMsDeleteCompany deleteCompanyCommand = new CompanyMsDeleteCompany();
		CompanyMsCompanyBean cmBean = wsCompanyDeleteRequest.getCompanyBean();
		deleteCompanyCommand.setCompanyMasterBean(cmBean);
		String commandData = CompanyMsJsonService.convertObjectToJson(deleteCompanyCommand);
		CompanyMsGenericCommand genericCommand = new CompanyMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(cmBean.getAggregateId());
		genericCommand.setCommandName(CompanyMsCommandType.DeleteCompany.getCommandName());
		return genericCommand;
	}

	@POST
	@Path("/CompanyMsCompanyDetailsService")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCompanyDetails(String companyDetailsRequest) {
		GtnFrameworkWebserviceResponse httpResponse = new GtnFrameworkWebserviceResponse();
		CompanyMsCompanyInventoryResponse inventoryResponse = new CompanyMsCompanyInventoryResponse();
		httpResponse.setStatus("Exception");
		httpResponse.setFailureReason("Event not received");
		System.out.println("CompanyMS : MVC Layer: Inside getCompanyDetails ");
		try {
			GtnFrameworkWebserviceRequest webserviceRequest = (GtnFrameworkWebserviceRequest) CompanyMsJsonService
					.convertJsonToObject(GtnFrameworkWebserviceRequest.class, companyDetailsRequest);

			CompanyMsCompanyInventoryRequest companyInventoryRequest = webserviceRequest.getCompanyInventoryRequest();
			CompanyMsCompanyBean companyBean = companyInventoryRequest.getCompanyBean();
			CompanyMsCompanyBean companyDetails = companyMsQueryModelGateway
					.getCompanyDetails(companyBean.getAggregateId());
			inventoryResponse.setCompanyBean(companyDetails);
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
			httpResponse.setSucess(true);
		} catch (IOException e) {
			httpResponse.setSucess(false);
			httpResponse.setFailureReason("Company MS MVC :Http Exception while processing getCompanies");
			e.printStackTrace();
		}
		httpResponse.setCompanyInventoryResponse(inventoryResponse);
		String convertedResponse = CompanyMsJsonService.convertObjectToJson(httpResponse);
		System.out.println("ComapnyMS : MVC Layer: getCompanies " + convertedResponse);
		return convertedResponse;
	}
}
