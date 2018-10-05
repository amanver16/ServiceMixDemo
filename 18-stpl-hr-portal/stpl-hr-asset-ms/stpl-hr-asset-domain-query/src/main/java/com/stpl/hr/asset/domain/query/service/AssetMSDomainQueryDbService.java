package com.stpl.hr.asset.domain.query.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.stpl.hr.asset.common.constant.AssetMsCommonConstant;
import com.stpl.hr.asset.domain.query.constants.AssetMsDomainQueryConstant;
import com.stpl.hr.asset.domain.query.data.bean.AssetMsAssetInventoryBean;

public class AssetMSDomainQueryDbService {

	private DataSource domainQueryDataSource;

	public AssetMSDomainQueryDbService() {
		super();
	}

	public void setDomainQueryDataSource(DataSource domainQueryDataSource) {
		this.domainQueryDataSource = domainQueryDataSource;
	}

	public void insertAsset(AssetMsAssetInventoryBean assetInventryData) throws SQLException, ParseException {
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement insertStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.ASSET_INVENTRY_INSERT_QUERY)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			insertStmt.setString(1, assetInventryData.getAssetId());
			insertStmt.setString(2, assetInventryData.getAssetAggregateId());
			insertStmt.setString(3, assetInventryData.getAssetType());
			insertStmt.setString(4, assetInventryData.getAssetSepcification());

			insertStmt.setString(5, assetInventryData.getAssetStatus());
			insertStmt.setString(6, assetInventryData.getAssetOwner());
			insertStmt.setString(7, assetInventryData.getAssetOwnerId());

			Date startDate = null;
			if (assetInventryData.getAssetStartDate() != null) {
				startDate = new Date(dateFormat.parse(assetInventryData.getAssetStartDate()).getTime());
			}
			insertStmt.setDate(8, startDate);

			Date endDate = null;
			if (assetInventryData.getAssetEndDate() != null) {
				endDate = new Date(dateFormat.parse(assetInventryData.getAssetEndDate()).getTime());
			}
			insertStmt.setDate(9, endDate);

			insertStmt.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in insertAsset ");
			e.printStackTrace();
		}
	}

	public void allocateAsset(AssetMsAssetInventoryBean assetInventryData) throws SQLException, ParseException {
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement insertStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.ASSET_ALLOCATE_QUERY)) {

			System.out.println("Asset MS Domain query Model : " + assetInventryData.getAssetId());
			System.out.println("Asset MS Domain query Model : " + assetInventryData.getAssetStatus());
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			insertStmt.setString(1, assetInventryData.getAssetStatus());
			insertStmt.setString(2, assetInventryData.getAssetOwner());
			insertStmt.setString(3, assetInventryData.getAssetOwnerId());
			Date startDate = null;
			if (assetInventryData.getAssetStartDate() != null) {
				startDate = new Date(dateFormat.parse(assetInventryData.getAssetStartDate()).getTime());
			}
			insertStmt.setDate(4, startDate);
			insertStmt.setString(5, assetInventryData.getAssetId());
			insertStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in allocateAsset ");
			e.printStackTrace();
		}
	}

	public String getVacantAsset(String assetType) throws SQLException {
		String vacantAssetAggregate = AssetMsCommonConstant.NO_VACANT_AGGREGATE;
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.ASSET_SELECT_VACANT_ASSET);) {
			selectStmt.setString(1, assetType);
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					vacantAssetAggregate = resultSet.getString(1);
				}
			}
		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in getVacantAsset ");
			e.printStackTrace();
		} finally {

		}
		return vacantAssetAggregate;
	}

	public boolean isDuplicateAssetId(String assetId) throws SQLException {
		boolean isDuplicateAssetId = false;
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.DUPLICATE_ASSET_QUERY);) {
			selectStmt.setString(1, assetId);
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					isDuplicateAssetId = !(resultSet.getInt(1) == 0);
				}
			}
		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in isDuplicateAssetId ");
			e.printStackTrace();
		} finally {

		}
		return isDuplicateAssetId;
	}

	public AssetMsAssetInventoryBean getAssetByAggregateId(String aggtegateId) throws SQLException {
		AssetMsAssetInventoryBean assetInventryBean = null;
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.ASSET_INVENTRY_SELECT_QUERY_BY_AGGREAGETID);) {
			selectStmt.setString(1, aggtegateId);
			SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					assetInventryBean = new AssetMsAssetInventoryBean();
					assetInventryBean.setAssetId(resultSet.getString(2));
					assetInventryBean.setAssetAggregateId(resultSet.getString(3));
					assetInventryBean.setAssetType(resultSet.getString(4));
					assetInventryBean.setAssetSepcification(resultSet.getString(5));
					assetInventryBean.setAssetStatus(resultSet.getString(6));
					assetInventryBean.setAssetOwner(resultSet.getString(7));
					String startDate = null;
					if (resultSet.getDate(8) != null) {
						startDate = dateFormat.format(new Date(resultSet.getDate(8).getTime()));
					}
					assetInventryBean.setAssetStartDate(startDate);

					String endDate = null;
					if (resultSet.getDate(9) != null) {
						endDate = dateFormat.format(new Date(resultSet.getDate(9).getTime()));
					}
					assetInventryBean.setAssetEndDate(endDate);
				}
			}
		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in getAssetByAggregateId ");
			e.printStackTrace();
		} finally {

		}
		return assetInventryBean;
	}

	public List<AssetMsAssetInventoryBean> getAllAsset() throws SQLException {
		List<AssetMsAssetInventoryBean> assetInventryBeanList = new ArrayList<>();
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(AssetMsDomainQueryConstant.ASSET_INVENTRY_SELECT_ALL_QUERY);) {
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(AssetMsCommonConstant.DATEFORMAT_EVENTTIME);
				while (resultSet.next()) {
					AssetMsAssetInventoryBean assetInventryBean = new AssetMsAssetInventoryBean();
					assetInventryBean = new AssetMsAssetInventoryBean();
					assetInventryBean.setAssetId(resultSet.getString(2));
					assetInventryBean.setAssetAggregateId(resultSet.getString(3));
					assetInventryBean.setAssetType(resultSet.getString(4));
					assetInventryBean.setAssetSepcification(resultSet.getString(5));
					assetInventryBean.setAssetStatus(resultSet.getString(6));
					assetInventryBean.setAssetOwner(resultSet.getString(7));
					String startDate = null;
					if (resultSet.getDate(8) != null) {
						startDate = dateFormat.format(new Date(resultSet.getDate(8).getTime()));
					}
					assetInventryBean.setAssetStartDate(startDate);

					String endDate = null;
					if (resultSet.getDate(9) != null) {
						endDate = dateFormat.format(new Date(resultSet.getDate(9).getTime()));
					}
					assetInventryBean.setAssetEndDate(endDate);
					assetInventryBeanList.add(assetInventryBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Asset MS Domain query Model : Error in getAllAsset ");
			e.printStackTrace();
		}
		return assetInventryBeanList;
	}

}
