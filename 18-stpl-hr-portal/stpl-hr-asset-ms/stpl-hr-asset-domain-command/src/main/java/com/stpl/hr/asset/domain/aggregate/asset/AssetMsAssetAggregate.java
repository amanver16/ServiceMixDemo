package com.stpl.hr.asset.domain.aggregate.asset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stpl.hr.asset.common.service.AssetMsDateService;
import com.stpl.hr.asset.domain.aggregate.AssetMsAggregateType;
import com.stpl.hr.asset.domain.aggregate.asset.entity.AssetAllocationProfile;
import com.stpl.hr.asset.domain.aggregate.asset.service.AssetMsService;
import com.stpl.hr.asset.domain.aggregate.asset.type.AssetMsAssetStatus;
import com.stpl.hr.asset.domain.aggregate.asset.type.AssetMsAssetType;
import com.stpl.hr.asset.domain.command.AssetMSAllocateAsset;
import com.stpl.hr.asset.domain.command.AssetMsAddAssetToInventory;
import com.stpl.hr.asset.domain.command.AssetMsCommandType;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.event.AssetMSAssetAdded;
import com.stpl.hr.asset.domain.event.AssetMsAssetAddingFailed;
import com.stpl.hr.asset.domain.event.AssetMsAssetAllocated;
import com.stpl.hr.asset.domain.event.AssetMsAssetAllocationFailed;
import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsAssetAggregate {

	private String aggregateId;
	private String assetId = null;
	private AssetMsAssetType assetType = null;
	private String assetSpecification = null;
	private AssetMsAssetStatus assetStatus;
	private AssetAllocationProfile currentProfile;

	public AssetMsAssetAggregate(String aggregateId) {
		super();
		this.aggregateId = aggregateId;
		this.assetStatus = AssetMsAssetStatus.NOTADDED;
	}

	public void applyEvent(AssetMsGenericEvent eventToBeApplied) throws IOException {

		this.aggregateId = eventToBeApplied.getAggregateId();
		if (eventToBeApplied.getEventName().equals(AssetMsEventType.AssetAdded.getEventName())) {

			AssetMSAssetAdded assetAdded = (AssetMSAssetAdded) AssetMSJsonService
					.convertJsonToObject(AssetMSAssetAdded.class, eventToBeApplied.getEventData());
			this.assetId = assetAdded.getAssetId();
			this.assetType = AssetMsAssetType.valueOf(assetAdded.getAssetType());
			this.assetStatus = AssetMsAssetStatus.valueOf(assetAdded.getAssetStatus());
			this.assetSpecification = assetAdded.getAssetSpecification();
			this.assetStatus = AssetMsAssetStatus.VACANT;
			return;

		}

		return;

	}

	public List<AssetMsGenericEvent> handleCommand(AssetMsGenericCommand genericCommand, AssetMsService assetMsService)
			throws IOException {

		List<AssetMsGenericEvent> eventList = new ArrayList<>();

		if (genericCommand.getCommandName().equals(AssetMsCommandType.addAssetToInventory.getCommandName())) {

			AssetMsAddAssetToInventory addAssetToInventory = (AssetMsAddAssetToInventory) AssetMSJsonService
					.convertJsonToObject(AssetMsAddAssetToInventory.class, genericCommand.getCommandData());

			this.assetId = addAssetToInventory.getAssetId();
			assignAssetType(addAssetToInventory);
			this.assetSpecification = addAssetToInventory.getAssetSpecification();
			if ((this.assetId == null) || (this.assetType == null) || (this.assetSpecification == null)) {
				System.out.println("Asset MS : Domain Command : assetId " + this.assetId + " assetType "
						+ this.assetType + " specification " + this.assetSpecification);
				addAssetAddingFailedEvent(eventList, genericCommand, addAssetToInventory);
			} else {
				this.assetStatus = AssetMsAssetStatus.VACANT;
				addAssedAddedEvent(eventList, genericCommand, addAssetToInventory, assetMsService);
			}

		}

		if (genericCommand.getCommandName().equals(AssetMsCommandType.allocateAsset.getCommandName())) {
			AssetMSAllocateAsset allocateAsset = (AssetMSAllocateAsset) AssetMSJsonService
					.convertJsonToObject(AssetMSAllocateAsset.class, genericCommand.getCommandData());
			if (this.assetStatus == AssetMsAssetStatus.VACANT) {
				this.currentProfile = new AssetAllocationProfile();
				currentProfile.setAssetOwner(allocateAsset.getAssetOwner());
				currentProfile.setAssetOwnerId(allocateAsset.getAssetOwnerId());
				currentProfile.setStartDate(AssetMsDateService.getCurrentDateInString());
				currentProfile.setEndDate(null);
				this.assetStatus = AssetMsAssetStatus.OCCUPIED;
				addAssetAllocatedEvent(eventList, genericCommand, allocateAsset, assetMsService);
			} else {
				addAssetAllocationFailedEvent(eventList, genericCommand, allocateAsset);
			}

		}
		return eventList;

	}

	private void assignAssetType(AssetMsAddAssetToInventory addAssetToInventory) {
		try {
			System.out.println("Asset MS : Domain Command : AssetType " + addAssetToInventory.getAssetType());
			this.assetType = AssetMsAssetType.valueOf(addAssetToInventory.getAssetType());

		} catch (Exception e) {
			this.assetType = null;
		}
	}

	private void addAssedAddedEvent(List<AssetMsGenericEvent> eventList, AssetMsGenericCommand genericCommand,
			AssetMsAddAssetToInventory addAssetToInventory, AssetMsService assetMsService) {

		AssetMSAssetAdded assetAddedEvent = new AssetMSAssetAdded();

		assetAddedEvent.setAssetId(this.assetId);
		assetAddedEvent.setAssetSpecification(this.assetSpecification);
		assetAddedEvent.setAssetType(this.assetType.getType());
		assetAddedEvent.setAssetStatus(this.assetStatus.getStatus());

		AssetMsGenericEvent genericEvent = buildGenericEvent(genericCommand, AssetMsEventType.AssetAdded,
				assetAddedEvent);
		eventList.add(genericEvent);

	}

	private void addAssetAddingFailedEvent(List<AssetMsGenericEvent> eventList, AssetMsGenericCommand genericCommand,
			AssetMsAddAssetToInventory addAssetToInventory) {

		AssetMsAssetAddingFailed assetAddingFailed = new AssetMsAssetAddingFailed();

		assetAddingFailed.setAssetId(this.assetId);
		assetAddingFailed.setFailureReason("AssetType " + addAssetToInventory.getAssetType() + " is not valid");
		AssetMsGenericEvent genericEvent = buildGenericEvent(genericCommand, AssetMsEventType.AssetAddingFailed,
				assetAddingFailed);
		eventList.add(genericEvent);

	}

	private AssetMsGenericEvent buildGenericEvent(AssetMsGenericCommand genericCommand, AssetMsEventType eventType,
			Object eventObject) {
		AssetMsGenericEvent genericEvent = new AssetMsGenericEvent();
		genericEvent.setAggregateId(this.aggregateId);
		genericEvent.setAggregateType(AssetMsAggregateType.AssetAggregate.getAggregateType());
		genericEvent.setEventName(eventType.getEventName());
		genericEvent.setOriginAggregateId(genericCommand.getAggregateId());
		genericEvent.setOriginCommandRequestId(genericCommand.getOriginCommandRequestId());
		genericEvent.setOriginCommandName(genericCommand.getCommandName());
		genericEvent.setOriginIssuedTime(genericCommand.getIssuedTime());
		genericEvent.setEventData(AssetMSJsonService.convertObjectToJson(eventObject));
		return genericEvent;
	}

	private void addAssetAllocatedEvent(List<AssetMsGenericEvent> eventList, AssetMsGenericCommand genericCommand,
			AssetMSAllocateAsset allocateAsset, AssetMsService assetMsService) {

		AssetMsAssetAllocated assetAllocatedEvent = new AssetMsAssetAllocated();

		assetAllocatedEvent.setAssetId(this.assetId);
		assetAllocatedEvent.setAssetStatus(this.assetStatus.getStatus());
		assetAllocatedEvent.setAssetOwner(this.currentProfile.getAssetOwner());
		assetAllocatedEvent.setAssetOwnerId(this.currentProfile.getAssetOwnerId());
		assetAllocatedEvent.setAssetStartDate(this.currentProfile.getStartDate());
		assetAllocatedEvent.setAssetEndDate(this.currentProfile.getEndDate());

		AssetMsGenericEvent genericEvent = buildGenericEvent(genericCommand, AssetMsEventType.AssetAllocated,
				assetAllocatedEvent);
		eventList.add(genericEvent);

	}

	private void addAssetAllocationFailedEvent(List<AssetMsGenericEvent> eventList,
			AssetMsGenericCommand genericCommand, AssetMSAllocateAsset allocateAsset) {

		AssetMsAssetAllocationFailed assetAllocationFailed = new AssetMsAssetAllocationFailed();
		assetAllocationFailed.setFailureReason("No more vacant assets. Please contact HR/IT Support");
		assetAllocationFailed.setAssetOwner(allocateAsset.getAssetOwner());
		assetAllocationFailed.setAssetOwnerId(allocateAsset.getAssetOwnerId());

		AssetMsGenericEvent genericEvent = buildGenericEvent(genericCommand, AssetMsEventType.AssetAllocationFailed,
				assetAllocationFailed);
		eventList.add(genericEvent);

	}

}
