package com.stpl.hr.asset.domain.aggregate.asset.processmanager;

import java.io.IOException;
import java.util.UUID;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.asset.common.constant.AssetMsCommonConstant;
import com.stpl.hr.asset.common.service.AssetMsDateService;
import com.stpl.hr.asset.domain.aggregate.asset.service.AssetMsService;
import com.stpl.hr.asset.domain.command.AssetMSAllocateAsset;
import com.stpl.hr.asset.domain.command.AssetMsCommandType;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.event.AssetMsOnbaordingInitiated;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsAssetProcessManager {

	@Produce(uri = "jms:topic:loginMsCommandBus")
	ProducerTemplate assetMsCommandProducer;
	
	private AssetMsService assetMsService;

	public AssetMsService getAssetMsService() {
		return assetMsService;
	}



	public void setAssetMsService(AssetMsService assetMsService) {
		this.assetMsService = assetMsService;
	}



	public void handleEvent(String content) throws IOException {
		System.out.println("AssetMs : Command : AssetProcessManager : Receiving event " + content);
		AssetMsGenericEvent event = AssetMSJsonService.buildEventFromJson(content);
		try {

			if (event.getEventName().equals(AssetMsEventType.OnBoardInitiated.getEventName())) {

				String vacantAssetAggregateId = assetMsService.queryVacantAssetAggregateId("DESKTOP");
				if ( AssetMsCommonConstant.NO_VACANT_AGGREGATE.equals(vacantAssetAggregateId)){
					vacantAssetAggregateId = UUID.randomUUID().toString();
				}
				AssetMsGenericCommand genericCommand = new AssetMsGenericCommand();

				genericCommand.setAggregateId(vacantAssetAggregateId);
				genericCommand.setCommandName(AssetMsCommandType.allocateAsset.getCommandName());
				genericCommand.setOriginEventRaisedTime(event.getRaisedTime());
				genericCommand.setOriginEventAggregateId(event.getOriginAggregateId());
				genericCommand.setOriginEventName(event.getEventName());

				AssetMsOnbaordingInitiated onboardInitiated = (AssetMsOnbaordingInitiated) AssetMSJsonService
						.convertJsonToObject(AssetMsOnbaordingInitiated.class, event.getEventData());
				AssetMSAllocateAsset allocateAsset = new AssetMSAllocateAsset();				
				allocateAsset.setAssetOwner(onboardInitiated.getFirstName()+"."+onboardInitiated.getLastName());
				allocateAsset.setAssetOwnerId(onboardInitiated.getAssociateId());
				genericCommand.setCommandData(AssetMSJsonService.convertObjectToJson(allocateAsset));
				genericCommand.setIssuedTime(AssetMsDateService.getCurrentDateInString());

				String commandJson = AssetMSJsonService.convertObjectToJson(genericCommand);
				System.out.println("AssetMs : Command : AssetProcessManager : Issuing command with commandName "
						+ genericCommand.getCommandName() + " commandJson " + commandJson);
				assetMsCommandProducer.sendBody(commandJson);

			} else {
				System.out.println("AssetMs : Command : AssetProcessManager : Ignoring event " + event.getEventName());
			}

		} catch (Exception e) {
			System.out.println("AssetMs : Command : AssetProcessManager : Error in storing event ");
			e.printStackTrace();
		}
	}
}
