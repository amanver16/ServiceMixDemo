package com.stpl.hr.asset.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.stpl.hr.asset.bus.consumer.AssetMsEventTracker;
import com.stpl.hr.asset.bus.gateway.AssetMsQueryModelGateway;
import com.stpl.hr.asset.bus.producer.AssetMsCommandProducer;
import com.stpl.hr.asset.domain.command.AssetMsAddAssetToInventory;
import com.stpl.hr.asset.domain.command.AssetMsCommandType;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.event.AssetMSAssetAdded;
import com.stpl.hr.asset.domain.event.AssetMsAssetAddingFailed;
import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;
import com.stpl.hr.asset.mvc.request.AssetMSAssetInventoryRequest;
import com.stpl.hr.asset.mvc.response.AssetMSAssetInventoryResponse;

@Path("/AssetMs")
public class AssetMsAssetController {

	public AssetMsAssetController() {
		super();
	}

	private AssetMsQueryModelGateway assetMsQueryModelGateway;

	private AssetMsCommandProducer assetMsCommandProducer;

	private AssetMsEventTracker assetMsEventTracker;

	public void setAssetMsQueryModelGateway(AssetMsQueryModelGateway assetMsQueryModelGateway) {
		this.assetMsQueryModelGateway = assetMsQueryModelGateway;
	}

	public void setAssetMsCommandProducer(AssetMsCommandProducer assetMsCommandProducer) {
		this.assetMsCommandProducer = assetMsCommandProducer;
	}

	public void setAssetMsEventTracker(AssetMsEventTracker assetMsEventTracker) {
		this.assetMsEventTracker = assetMsEventTracker;
	}

	@POST
	@Path("/addAsset")
	@Produces(MediaType.APPLICATION_JSON)
	public String addAsset(String assetDetails) {
		AssetMSAssetInventoryResponse assetInventoryResponse = new AssetMSAssetInventoryResponse();
		assetInventoryResponse.setStatus("Exception");
		assetInventoryResponse.setFailureReason("Event not received");
		try {

			AssetMSAssetInventoryRequest assetInventoryRequest = (AssetMSAssetInventoryRequest) AssetMSJsonService
					.convertJsonToObject(AssetMSAssetInventoryRequest.class, assetDetails);
			if (assetMsQueryModelGateway.isAssetIdDuplicate(assetInventoryRequest.getAssetId())) {

				assetInventoryResponse.setStatus("Failure");
				assetInventoryResponse.setFailureReason(
						"Asset Serial Id " + assetInventoryRequest.getAssetId() + " is already added");
				String convertedResponse = AssetMSJsonService.convertObjectToJson(assetInventoryResponse);
				System.out.println(
						"Asset MS MVC Layer: Without triggering command and event Response to UI " + convertedResponse);
				return convertedResponse;

			}
			AssetMsGenericCommand genericCommand = buildAssetInventoryCommand(assetInventoryRequest);
			assetMsCommandProducer.initiateCommand(genericCommand);
			assetMsEventTracker.addRequestIdInMap(genericCommand.getOriginCommandRequestId());

			AssetMsGenericEvent eventResponse = assetMsEventTracker
					.pollEvent(genericCommand.getOriginCommandRequestId(), 30);

			System.out.println("Asset MS MVC : Received response " + eventResponse);

			if (eventResponse != null) {

				buildAssetMsResponse(assetInventoryResponse, eventResponse);

			}
		} catch (IOException e) {
			assetInventoryResponse.setFailureReason("Asset MS MVC :Http Exception while processing addAsset");
			e.printStackTrace();
		}
		String convertedResponse = AssetMSJsonService.convertObjectToJson(assetInventoryResponse);
		return convertedResponse;

	}

	@GET
	@Path("/getAllAssets")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAssets() {
		System.out.println("AssetMS : MVC Layer: Inside getAllAssets ");
		List<AssetMsAssetInventoryBean> associateList = assetMsQueryModelGateway.getAllAssets();
		String convertedResponse = AssetMSJsonService.convertObjectToJson(associateList);
		System.out.println("AssetMS : MVC Layer: getAllAssets " + convertedResponse);
		return convertedResponse;

	}

	private AssetMsGenericCommand buildAssetInventoryCommand(AssetMSAssetInventoryRequest assesetInventorytRequest) {
		AssetMsAddAssetToInventory initiateAssetToInventoryCommand = new AssetMsAddAssetToInventory();
		initiateAssetToInventoryCommand.setAssetSpecification(assesetInventorytRequest.getSpecification());
		initiateAssetToInventoryCommand.setAssetType(assesetInventorytRequest.getAssetType());
		initiateAssetToInventoryCommand.setAssetId(assesetInventorytRequest.getAssetId());

		String commandData = AssetMSJsonService.convertObjectToJson(initiateAssetToInventoryCommand);

		AssetMsGenericCommand genericCommand = new AssetMsGenericCommand();
		genericCommand.setCommandData(commandData);
		genericCommand.setOriginCommandRequestId(UUID.randomUUID().toString());
		genericCommand.setAggregateId(assetMsQueryModelGateway.getAssetAggregateId());
		genericCommand.setCommandName(AssetMsCommandType.addAssetToInventory.getCommandName());
		return genericCommand;
	}

	private void buildAssetMsResponse(AssetMSAssetInventoryResponse httpResponse, AssetMsGenericEvent eventResponse)
			throws IOException {
		httpResponse.setStatus("Failure");
		httpResponse.setFailureReason("Asset Addition Failure");
		if (eventResponse.getEventName().equals(AssetMsEventType.AssetAdded.getEventName())) {
			AssetMSAssetAdded assetAddedEventData = (AssetMSAssetAdded) AssetMSJsonService
					.convertJsonToObject(AssetMSAssetAdded.class, eventResponse.getEventData());
			httpResponse.setAssetId(assetAddedEventData.getAssetId());
			httpResponse.setStatus("Success");
			httpResponse.setFailureReason("");
		}
		if (eventResponse.getEventName().equals(AssetMsEventType.AssetAddingFailed.getEventName())) {
			AssetMsAssetAddingFailed assetAdditionFailedEventData = (AssetMsAssetAddingFailed) AssetMSJsonService
					.convertJsonToObject(AssetMsAssetAddingFailed.class, eventResponse.getEventData());
			httpResponse.setAssetId(assetAdditionFailedEventData.getAssetId());
			httpResponse.setFailureReason(assetAdditionFailedEventData.getFailureReason());
		}
	}

}
