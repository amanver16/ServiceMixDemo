package com.stpl.hr.asset.domain.query.handler;

import java.sql.SQLException;
import java.util.List;

import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;
import com.stpl.hr.asset.domain.query.mongo.service.AssetMSDomainQueryMongoDbService;

public class AssetMSDomainQueryGatewayHandler {

	private AssetMSDomainQueryMongoDbService domainQueryDbSevice;

	public void setDomainQueryDbSevice(AssetMSDomainQueryMongoDbService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public String getVacantAsset(String assetType) throws SQLException {
		return domainQueryDbSevice.getVacantAsset(assetType);
	}

	public List<AssetMsAssetInventoryBean> getAllAsset() throws SQLException {
		return domainQueryDbSevice.getAllAsset();
	}

	public boolean checkDuplicateAssetId(String assetId) throws SQLException {
		return domainQueryDbSevice.isDuplicateAssetId(assetId);
	}

}
