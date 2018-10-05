package com.stpl.hr.login.domainevent.store.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.stpl.hr.login.domain.event.LoginMsGenericEvent;
import com.stpl.hr.login.domain.event.data.LoginMsAggregate;
import com.stpl.hr.login.domain.event.data.LoginMsAggregateEvent;

public class LoginMsEventStoreCommandService {

	public LoginMsEventStoreCommandService() {
		super();
	}

	private DataSource commandDataSource;

	public void setCommandDataSource(DataSource commandDataSource) {
		this.commandDataSource = commandDataSource;
	}

	public int checkForDuplicateAggregate(String aggregateId) throws SQLException {
		String duplicateCheckQuery = "SELECT count(*) FROM dbo.THR_LOGIN_AGGREGATE where AGGREGATE_ID='" + aggregateId
				+ "';";
		int returnValue = 0;
		try (Connection connection = commandDataSource.getConnection();
				Statement sta = connection.createStatement();
				ResultSet resultSet = sta.executeQuery(duplicateCheckQuery)) {

			if (resultSet.next()) {
				returnValue = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("LoginMs : Event Store-Write Model : Error in checkForDuplicateAggregate ");
			e.printStackTrace();
		}
		return returnValue;
	}

	public void addLoginAggregate(LoginMsGenericEvent loginAggregateData) {
		String insertQuery = "INSERT into THR_LOGIN_AGGREGATE(AGGREGATE_ID,AGGREGATE_TYPE) values('"
				+ loginAggregateData.getAggregateId() + "','" + "User" + "');";
		try (Connection connection = commandDataSource.getConnection();
				Statement insertStatement = connection.createStatement()) {
			insertStatement.executeUpdate(insertQuery);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("LoginMs : Event Store-Write Model : Error in addLoginAggregate ");
			e.printStackTrace();
		}
	}

	public void addLoginAggregateEvent(LoginMsGenericEvent loginAggregateEventData, String content)
			throws SQLException {
		String insertQuery = "INSERT into THR_LOGIN_AGGREGATE_EVENT(AGGREGATE_ID,EVENT_DATA,EVENT_NAME,VERSION) values('"
				+ loginAggregateEventData.getAggregateId() + "','" + content + "','"
				+ loginAggregateEventData.getEventName() + "','" + loginAggregateEventData.getVersion() + "');";
		try (Connection connection = commandDataSource.getConnection();
				Statement insertStatement = connection.createStatement()) {
			insertStatement.executeUpdate(insertQuery);
			connection.commit();
		} catch (SQLException e) {
			System.out.println("LoginMs : Event Store-Write Model : Error in addLoginAggregateEvent ");
			e.printStackTrace();
		}
	}

	public LoginMsAggregate getAggregateDetails(String aggregateId) {

		LoginMsAggregate loginMsAggregate = null;
		try {
			loginMsAggregate = getAggregate(aggregateId);
			if (loginMsAggregate == null) {
				throw new Exception("Aggregate not found ");
			}
			getAggregateEvents(aggregateId, loginMsAggregate);

		} catch (Exception e) {
			loginMsAggregate = new LoginMsAggregate();
			loginMsAggregate.setAggregateId(aggregateId);
			loginMsAggregate.setAggregateNotFound(true);
			System.out.println(
					"LoginMs : Event Store-Write Model : aggregate Id not found in event store " + aggregateId);
			e.printStackTrace();

		}
		return loginMsAggregate;

	}

	private void getAggregateEvents(String aggregateId, LoginMsAggregate loginMsAggregate) throws SQLException {

		String getAggregateEventQuery = "SELECT EVENT_DATA,EVENT_NAME,VERSION FROM THR_LOGIN_AGGREGATE_EVENT WHERE AGGREGATE_ID='"
				+ aggregateId + "' ORDER BY CREATED_DATE;";

		try (Connection connection = commandDataSource.getConnection();
				Statement selectStatement = connection.createStatement();
				ResultSet eventAggregateResult = selectStatement.executeQuery(getAggregateEventQuery);) {

			LoginMsAggregateEvent aggregateEvent = null;
			while (eventAggregateResult.next()) {
				aggregateEvent = new LoginMsAggregateEvent();
				aggregateEvent.setEventData(eventAggregateResult.getString(1));
				aggregateEvent.setEventName(eventAggregateResult.getString(2));
				aggregateEvent.setVersion(eventAggregateResult.getString(3));
				loginMsAggregate.addAggregateEvent(aggregateEvent);
			}

		} catch (SQLException e) {
			System.out.println(
					"LoginMs : Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
			throw e;

		}

	}

	private LoginMsAggregate getAggregate(String aggregateId) {

		String getAggregateQuery = "SELECT AGGREGATE_ID,AGGREGATE_TYPE from THR_LOGIN_AGGREGATE WHERE AGGREGATE_ID='"
				+ aggregateId + "' ORDER BY CREATED_DATE;";
		LoginMsAggregate loginMsAggregate = null;
		try (Connection connection = commandDataSource.getConnection();
				Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(getAggregateQuery);

			if (resultSet.next()) {

				loginMsAggregate = new LoginMsAggregate();
				loginMsAggregate.setAggregateId(resultSet.getString(1));
				loginMsAggregate.setAggregateType(resultSet.getString(2));
			}

		} catch (SQLException e) {
			System.out.println(
					"LoginMs : Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return loginMsAggregate;
	}

}
