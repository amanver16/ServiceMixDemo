package com.stpl.hr.asset.domain.query.handler;

import java.io.IOException;

import com.stpl.hr.asset.domain.event.AssetMSAssetAdded;
import com.stpl.hr.asset.domain.event.AssetMsAssetAllocated;
import com.stpl.hr.asset.domain.event.AssetMsEventType;
import com.stpl.hr.asset.domain.event.AssetMsGenericEvent;
import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;
import com.stpl.hr.asset.domain.query.mongo.service.AssetMSDomainQueryMongoDbService;
import com.stpl.hr.asset.domain.service.AssetMSJsonService;

public class AssetMSDomainQueryHandler {

	public AssetMSDomainQueryHandler() {
		super();
	}

	private AssetMSDomainQueryMongoDbService domainQueryDbSevice;

	public void setDomainQueryDbSevice(AssetMSDomainQueryMongoDbService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public void handleEvent(String content) throws IOException {
		System.out.println("Asset MS Domain Query Model : Receiving event " + content);
		AssetMsGenericEvent event = AssetMSJsonService.buildEventFromJson(content);
		if (AssetMsEventType.AssetAdded.getEventName().equals(event.getEventName())) {
			handleAssetAddedEvent(event);
			return;
		}

		if (AssetMsEventType.AssetAllocated.getEventName().equals(event.getEventName())) {
			handleAssetAllocatedEvent(event);
			return;
		}
		System.out.println("Asset MS Domain Query Model: Ignoring event " + event.getEventName());
		return;

	}

	private void handleAssetAddedEvent(AssetMsGenericEvent event) {
		try {
			AssetMSAssetAdded eventObj = (AssetMSAssetAdded) AssetMSJsonService
					.convertJsonToObject(AssetMSAssetAdded.class, event.getEventData());
			AssetMsAssetInventoryBean associateDataBean = new AssetMsAssetInventoryBean();
			associateDataBean.setAssetAggregateId(event.getAggregateId());
			associateDataBean.setAssetId(eventObj.getAssetId());
			associateDataBean.setAssetSepcification(eventObj.getAssetSpecification());
			associateDataBean.setAssetType(eventObj.getAssetType());
			associateDataBean.setAssetStatus(eventObj.getAssetStatus());
			domainQueryDbSevice.insertAsset(associateDataBean);
			System.out.println("Asset MS Domain Query Model : Stored new Asset " + associateDataBean.getAssetId());

		} catch (Exception e) {
			System.out.println("AssetMS : Error In handleAssetAddedEvent");
			e.printStackTrace();
		}
		return;
	}

	private void handleAssetAllocatedEvent(AssetMsGenericEvent event) {

		try {
			AssetMsAssetAllocated eventObj = (AssetMsAssetAllocated) AssetMSJsonService
					.convertJsonToObject(AssetMsAssetAllocated.class, event.getEventData());
			AssetMsAssetInventoryBean assetInventryBean = domainQueryDbSevice
					.getAssetByAggregateId(event.getAggregateId());
			assetInventryBean.setAssetAggregateId(event.getAggregateId());
			assetInventryBean.setAssetId(eventObj.getAssetId());
			assetInventryBean.setAssetOwner(eventObj.getAssetOwner());
			assetInventryBean.setAssetOwnerId(eventObj.getAssetOwnerId());
			assetInventryBean.setAssetStatus(eventObj.getAssetStatus());
			assetInventryBean.setAssetStartDate(eventObj.getAssetStartDate());
			assetInventryBean.setAssetEndDate(eventObj.getAssetEndDate());
			domainQueryDbSevice.allocateAsset(assetInventryBean);
			System.out.println("AssetMS : Read Model : AssetAllocatedEvent " + assetInventryBean.getAssetId());

		} catch (Exception e) {
			System.out.println("AssetMS : Read Model : Error AssetAllocatedEvent");
			e.printStackTrace();
		}
		return;
	}

}
