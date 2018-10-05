package com.stpl.hr.onboard.domain.event.store.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.stpl.hr.onboard.domain.event.OnboardMsGenericEvent;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateDao;
import com.stpl.hr.onboard.domain.event.data.OnboardMsAggregateEventDao;
import com.stpl.hr.onboard.domain.event.store.constants.OnboardMsEventStoreConstant;

public class OnboardMsEventStoreDbService {

	public OnboardMsEventStoreDbService() {
		super();
	}

	private DataSource eventDataSource;

	public void setEventDataSource(DataSource eventDataSource) {
		this.eventDataSource = eventDataSource;
	}

	public int checkForDuplicateAggregate(String aggregateId) throws SQLException {
		String duplicateCheckQuery = OnboardMsEventStoreConstant.GET_AGGREGATE_COUNT;
		int returnValue = 0;
		try (Connection connection = eventDataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(duplicateCheckQuery);) {

			statement.setString(1, aggregateId);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {
					returnValue = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in checkForDuplicateAggregate ");
			e.printStackTrace();
		}
		return returnValue;
	}

	public void addAggregate(OnboardMsGenericEvent aggregateData) {
		String insertQuery = OnboardMsEventStoreConstant.INSERT_AGGREGATE;
		try (Connection connection = eventDataSource.getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
			insertStatement.setString(1, aggregateData.getAggregateId());
			insertStatement.setString(2, OnboardMsEventStoreConstant.ASSOCIATE);
			insertStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in addAggregate ");
			e.printStackTrace();
		}
	}

	public void addAggregateEvent(OnboardMsGenericEvent aggregateEventData, String content) throws SQLException {
		String insertQuery = OnboardMsEventStoreConstant.INSERT_AGGREGATE_EVENT;
		try (Connection connection = eventDataSource.getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

			insertStatement.setString(1, aggregateEventData.getAggregateId());
			insertStatement.setString(2, content);
			insertStatement.setString(3, aggregateEventData.getEventName());
			insertStatement.setString(4, aggregateEventData.getVersion());
			insertStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			System.out.println("Onboard MS Event Store-Write Model : Error in addAggregateEvent ");
			e.printStackTrace();
		}
	}

	public OnboardMsAggregateDao getAssociateAggregateEvents(String aggregateId) {

		OnboardMsAggregateDao aggregate = null;
		try {
			aggregate = getAggregate(aggregateId);
			if (aggregate == null) {
				throw new Exception("Onboard MS Aggregate not found ");
			}
			getAggregateEvents(aggregateId, aggregate);

		} catch (Exception e) {
			aggregate = new OnboardMsAggregateDao();
			aggregate.setAggregateId(aggregateId);
			aggregate.setAggregateNotFound(true);
			System.out.println(
					"Onboard MS Event Store-Write Model : aggregate Id not found in event store " + aggregateId);

		}
		return aggregate;

	}

	private OnboardMsAggregateDao getAggregate(String aggregateId) {

		String getAggregateQuery = OnboardMsEventStoreConstant.GET_AGGREGATE_DATA;
		OnboardMsAggregateDao aggregate = null;
		try (Connection connection = eventDataSource.getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(getAggregateQuery)) {

			selectStatement.setString(1, aggregateId);

			try (ResultSet resultSet = selectStatement.executeQuery()) {

				if (resultSet.next()) {

					aggregate = new OnboardMsAggregateDao();
					aggregate.setAggregateId(resultSet.getString(1));
					aggregate.setAggregateType(resultSet.getString(2));
				}
			}

		} catch (SQLException e) {
			System.out.println(
					"Onboard MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();

		}
		return aggregate;
	}

	private void getAggregateEvents(String aggregateId, OnboardMsAggregateDao aggregate) throws SQLException {

		String getAggregateEventQuery = OnboardMsEventStoreConstant.GET_AGGREGATE_EVENT_DATA;

		try (Connection connection = eventDataSource.getConnection();
				PreparedStatement selectStatement = connection.prepareStatement(getAggregateEventQuery)) {

			selectStatement.setString(1, aggregateId);

			try (ResultSet eventAggregateResult = selectStatement.executeQuery();) {

				OnboardMsAggregateEventDao aggregateEvent = null;
				while (eventAggregateResult.next()) {
					aggregateEvent = new OnboardMsAggregateEventDao();
					aggregateEvent.setEventData(eventAggregateResult.getString(1));
					aggregateEvent.setEventName(eventAggregateResult.getString(2));
					aggregateEvent.setVersion(eventAggregateResult.getString(3));
					aggregate.addAggregateEvent(aggregateEvent);
				}

			}

		} catch (SQLException e) {
			System.out.println(
					"Onboard MS Event Store-Write Model : Error in fetching events from event Store  " + aggregateId);
			e.printStackTrace();
			throw e;

		}

	}

}
