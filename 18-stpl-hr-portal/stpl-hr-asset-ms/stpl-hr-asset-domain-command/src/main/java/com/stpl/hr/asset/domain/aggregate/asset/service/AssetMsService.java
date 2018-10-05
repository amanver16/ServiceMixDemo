package com.stpl.hr.asset.domain.aggregate.asset.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class AssetMsService {

	@Produce(uri = "direct-vm:queryVacantAssetAggregateId")
	ProducerTemplate queryVacantAssetAggregateId;

	public String queryVacantAssetAggregateId(String assetType) {
		
		String vacantAggregateId = (String) queryVacantAssetAggregateId.requestBody(assetType);
		System.out.println("AssetMS: AssetMsAssociateService: vacantAggregateId:" + vacantAggregateId);
		
		return vacantAggregateId;
	}


}
