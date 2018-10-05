package com.stpl.hr.asset.bus.gateway;

import java.util.List;
import java.util.UUID;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;

public class AssetMsQueryModelGateway {

	public AssetMsQueryModelGateway() {
		super();
	}

	@Produce(uri = "direct-vm:queryAllAsset")
	private ProducerTemplate queryAllAssets;

	@Produce(uri = "direct-vm:checkDuplicateAssetId")
	private ProducerTemplate duplicateCheckTemplate;

	@SuppressWarnings("unchecked")
	public List<AssetMsAssetInventoryBean> getAllAssets() {
		Object response = queryAllAssets.requestBody("");
		List<AssetMsAssetInventoryBean> assetList = (List<AssetMsAssetInventoryBean>) response;
		return assetList;
	}

	public String getAssetAggregateId() {
		String newAggregateId = UUID.randomUUID().toString();
		System.out.println("Asset MS MVC Layer: Generating New AggregateId for new asset " + newAggregateId);
		return newAggregateId;
	}

	public boolean isAssetIdDuplicate(String assetId) {
		Object response = duplicateCheckTemplate.requestBody(assetId);
		boolean isDuplicate = (boolean) response;
		System.out.println("Asset MS MVC Layer:isAssetIdDuplicate - " + isDuplicate);
		return isDuplicate;
	}
}
