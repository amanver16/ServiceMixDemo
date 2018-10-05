package com.stpl.hr.onboard.domain.query.service;

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

import com.stpl.hr.onboard.common.constant.OnboardMsCommonConstant;
import com.stpl.hr.onboard.domain.query.constants.OnboardMsDomainQueryConstant;
import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;

public class OnboardMSDomainQueryDbService {

	private DataSource domainQueryDataSource;

	public OnboardMSDomainQueryDbService() {
		super();
	}

	public void setDomainQueryDataSource(DataSource domainQueryDataSource) {
		this.domainQueryDataSource = domainQueryDataSource;
	}

	public void onboardAssociate(OnboardMsOnboardAssociateBean onboardData) throws SQLException, ParseException {
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement insertStmt = connection
						.prepareStatement(OnboardMsDomainQueryConstant.ONBOARD_ASSOCIATE_INSERT_QUERY)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			insertStmt.setLong(1, Long.parseLong(onboardData.getAssociateId()));
			insertStmt.setString(2, onboardData.getAggregateId());
			insertStmt.setString(3, onboardData.getFirstName());
			insertStmt.setString(4, onboardData.getLastName());

			insertStmt.setString(5, onboardData.getMobileNo());
			insertStmt.setString(6, onboardData.getEmailId());
			insertStmt.setString(7, onboardData.getHighestDegree());
			insertStmt.setString(8, onboardData.getCgpa());

			insertStmt.setString(9, onboardData.getCollegeName());
			insertStmt.setString(10, onboardData.getPreviousEmployer());
			insertStmt.setString(11, onboardData.getYearOfExperience());
			insertStmt.setString(12, onboardData.getBusinessUnit());
			insertStmt.setString(13, onboardData.getRole());
			insertStmt.setString(14, onboardData.getDesignation());
			insertStmt.setDate(15, new Date(dateFormat.parse(onboardData.getJoiningDate()).getTime()));
			insertStmt.setString(16, onboardData.getUserName());
			insertStmt.setString(17, onboardData.getStatus());
			insertStmt.setString(18, onboardData.getAssetInfo());
			insertStmt.executeUpdate();

			connection.commit();

		} catch (SQLException e) {
			System.out.println("Onboard MS Domain query Model : Error in onBoardCandidate ");
			e.printStackTrace();
		}
	}

	public OnboardMsOnboardAssociateBean getAssociateDetails(String associateId) throws SQLException {
		OnboardMsOnboardAssociateBean associateDataBean = null;
		System.out.println("Onboard MS Domain query Model : associateId " + associateId);
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(OnboardMsDomainQueryConstant.ONBOARD_ASSOCIATE_SELECT_QUERY);) {
			selectStmt.setString(1, associateId);
			SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {

					associateDataBean = new OnboardMsOnboardAssociateBean();
					associateDataBean.setAssociateId(String.valueOf(resultSet.getLong(2)));
					associateDataBean.setAggregateId(resultSet.getString(3));
					associateDataBean.setFirstName(resultSet.getString(4));
					associateDataBean.setLastName(resultSet.getString(5));
					associateDataBean.setMobileNo(resultSet.getString(6));
					associateDataBean.setEmailId(resultSet.getString(7));
					associateDataBean.setHighestDegree(resultSet.getString(8));
					associateDataBean.setCgpa(resultSet.getString(9));
					associateDataBean.setCollegeName(resultSet.getString(10));
					associateDataBean.setPreviousEmployer(resultSet.getString(11));
					associateDataBean.setYearOfExperience(resultSet.getString(12));
					associateDataBean.setBusinessUnit(resultSet.getString(13));
					associateDataBean.setRole(resultSet.getString(14));
					associateDataBean.setDesignation(resultSet.getString(15));
					associateDataBean.setJoiningDate(dateFormat.format(new Date(resultSet.getDate(16).getTime())));
					associateDataBean.setStatus(resultSet.getString(17));
					associateDataBean.setAssetInfo(resultSet.getString(18));
				}
			}
		} catch (SQLException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAssociateDetails ");
			e.printStackTrace();
		} finally {

		}
		return associateDataBean;
	}

	public List<OnboardMsOnboardAssociateBean> getAllAssociateDetails() throws SQLException {
		List<OnboardMsOnboardAssociateBean> associateDataBeanList = new ArrayList<>();
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(OnboardMsDomainQueryConstant.ONBOARD_ASSOCIATE_SELECT_ALL_QUERY);) {
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(OnboardMsCommonConstant.DATEFORMAT_JOINING_DATE);
				while (resultSet.next()) {
					OnboardMsOnboardAssociateBean associateDataBean = new OnboardMsOnboardAssociateBean();
					associateDataBean.setAssociateId(String.valueOf(resultSet.getLong(2)));
					associateDataBean.setAggregateId(resultSet.getString(3));
					associateDataBean.setFirstName(resultSet.getString(4));
					associateDataBean.setLastName(resultSet.getString(5));
					associateDataBean.setMobileNo(resultSet.getString(6));
					associateDataBean.setEmailId(resultSet.getString(7));
					associateDataBean.setHighestDegree(resultSet.getString(8));
					associateDataBean.setCgpa(resultSet.getString(9));
					associateDataBean.setCollegeName(resultSet.getString(10));
					associateDataBean.setPreviousEmployer(resultSet.getString(11));
					associateDataBean.setYearOfExperience(resultSet.getString(12));
					associateDataBean.setBusinessUnit(resultSet.getString(13));
					associateDataBean.setRole(resultSet.getString(14));
					associateDataBean.setDesignation(resultSet.getString(15));
					associateDataBean.setJoiningDate(dateFormat.format(new Date(resultSet.getDate(16).getTime())));
					associateDataBean.setStatus(resultSet.getString(17));
					associateDataBean.setAssetInfo(resultSet.getString(18));
					associateDataBean.setUserName(resultSet.getString(19));
					associateDataBeanList.add(associateDataBean);
				}
			}
		} catch (SQLException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAllAssociateDetails ");
			e.printStackTrace();
		}
		return associateDataBeanList;
	}

	public long getLatestAssociateId() throws SQLException {
		long latestAssociateId = 10000L;
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection
						.prepareStatement(OnboardMsDomainQueryConstant.ONBOARD_ASSOCIATE_SELECT_LATEST_ASSOCIATE);) {
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					latestAssociateId = resultSet.getLong(1);

				}
			}
		} catch (SQLException e) {
			System.out.println("Onboard MS Domain query Model : Error in getLatestAssociateId ");
			e.printStackTrace();
		}
		System.out.println("Onboard MS Domain query Model Getting the getLatestAssociateId-----" + latestAssociateId);
		return latestAssociateId;
	}

	public OnboardMsOnboardAssociateBean getAssociateDetailsByAggregateId(String aggregateId, String eventName)
			throws SQLException {
		OnboardMsOnboardAssociateBean associateDataBean = null;
		try (Connection connection = domainQueryDataSource.getConnection();
				PreparedStatement selectStmt = connection.prepareStatement(
						OnboardMsDomainQueryConstant.ONBOARD_ASSOCIATE_SELECT_QUERY_BY_AGGREAGETID);) {
			selectStmt.setString(1, aggregateId);
			selectStmt.setString(2, eventName);
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
			try (ResultSet resultSet = selectStmt.executeQuery()) {
				if (resultSet.next()) {
					associateDataBean = new OnboardMsOnboardAssociateBean();
					associateDataBean.setAssociateId(String.valueOf(resultSet.getLong(2)));
					associateDataBean.setAggregateId(resultSet.getString(3));
					associateDataBean.setFirstName(resultSet.getString(4));
					associateDataBean.setLastName(resultSet.getString(5));
					associateDataBean.setMobileNo(resultSet.getString(6));
					associateDataBean.setEmailId(resultSet.getString(7));
					associateDataBean.setHighestDegree(resultSet.getString(8));
					associateDataBean.setCgpa(resultSet.getString(9));
					associateDataBean.setCollegeName(resultSet.getString(10));
					associateDataBean.setPreviousEmployer(resultSet.getString(11));
					associateDataBean.setYearOfExperience(resultSet.getString(12));
					associateDataBean.setBusinessUnit(resultSet.getString(13));
					associateDataBean.setRole(resultSet.getString(14));
					associateDataBean.setDesignation(resultSet.getString(15));
					associateDataBean.setJoiningDate(dateFormat.format(new Date(resultSet.getDate(16).getTime())));
					associateDataBean.setStatus(resultSet.getString(17));
					associateDataBean.setAssetInfo(resultSet.getString(18));
				}
			}
		} catch (SQLException e) {
			System.out.println("Onboard MS Domain query Model : Error in getAssociateDetails ");
			e.printStackTrace();
		} finally {

		}
		return associateDataBean;
	}
}
