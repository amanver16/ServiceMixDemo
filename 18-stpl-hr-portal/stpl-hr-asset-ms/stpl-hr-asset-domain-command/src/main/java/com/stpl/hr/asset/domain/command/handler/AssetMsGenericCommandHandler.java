package com.stpl.hr.asset.domain.command.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stpl.hr.asset.bus.event.producer.AssetMsEventProducer;
import com.stpl.hr.asset.domain.aggregate.asset.AssetMsAssetAggregate;
import com.stpl.hr.asset.domain.aggregate.asset.loader.AssetMsAggregateLoader;
import com.stpl.hr.asset.domain.aggregate.asset.service.AssetMsService;
import com.stpl.hr.asset.domain.command.AssetMsCommandType;
import com.stpl.hr.asset.domain.command.AssetMsGenericCommand;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMsGenericCommandHandler {

	private AssetMsEventProducer assetMsEventProducer;

	private AssetMsAggregateLoader assetMsAggregateLoader;

	private AssetMsService assetMsService;

	private Map<String, AssetMsCommandType> supportedCommandMap;

	public AssetMsGenericCommandHandler() {
		supportedCommandMap = new HashMap<>();
		supportedCommandMap.put(AssetMsCommandType.addAssetToInventory.getCommandName(),
				AssetMsCommandType.addAssetToInventory);
		supportedCommandMap.put(AssetMsCommandType.allocateAsset.getCommandName(), AssetMsCommandType.allocateAsset);
	}

	public AssetMsEventProducer getAssetMsEventProducer() {
		return assetMsEventProducer;
	}

	public void setAssetMsEventProducer(AssetMsEventProducer assetMsEventProducer) {
		this.assetMsEventProducer = assetMsEventProducer;
	}

	public AssetMsAggregateLoader getAssetMsAggregateLoader() {
		return assetMsAggregateLoader;
	}

	public void setAssetMsAggregateLoader(AssetMsAggregateLoader assetMsAggregateLoader) {
		this.assetMsAggregateLoader = assetMsAggregateLoader;
	}

	public AssetMsService getAssetMsService() {
		return assetMsService;
	}

	public void setAssetMsService(AssetMsService assetMsService) {
		this.assetMsService = assetMsService;
	}

	public void handle(String commandString) {

		System.out.println("Asset MS : Command Handler : Receiving command " + commandString);
		try {

			AssetMsGenericCommand command = AssetMSJsonService.buildCommandFromJson(commandString);
			if (supportedCommandMap.get(command.getCommandName()) == null) {
				System.out.println("AssetMS: CommandHandler: IgnoringCommand: " + command.getCommandName());
				return;
			}
			AssetMsAssetAggregate aggregate = assetMsAggregateLoader.loadAggregate(command.getAggregateId());
			List<AssetMsGenericEvent> eventList = aggregate.handleCommand(command, assetMsService);
			for (AssetMsGenericEvent currentEvent : eventList) {
				assetMsEventProducer.raiseEvent(currentEvent);
			}

		} catch (Exception e) {
			System.out.println("Asset MS Command Handler : Error in handling command : " + commandString);
			e.printStackTrace();
		}
	}

}
