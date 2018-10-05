package com.stpl.hr.onboard.domain.event.store.constants;

public class OnboardMsEventStoreConstant {

	public static final String GET_AGGREGATE_COUNT = "SELECT count(*) FROM dbo.THR_ONBOARD_AGGREGATE where AGGREGATE_ID=?";
	public static final String INSERT_AGGREGATE = "INSERT into THR_ONBOARD_AGGREGATE(AGGREGATE_ID,AGGREGATE_TYPE) values(?,?)";
	public static final String INSERT_AGGREGATE_EVENT = "INSERT into THR_ONBOARD_AGGREGATE_EVENT(AGGREGATE_ID,EVENT_DATA,EVENT_NAME,VERSION) values(?,?,?,?)";
	public static final String GET_AGGREGATE_DATA = "SELECT AGGREGATE_ID,AGGREGATE_TYPE from THR_ONBOARD_AGGREGATE WHERE AGGREGATE_ID= ? ORDER BY CREATED_DATE";
	public static final String GET_AGGREGATE_EVENT_DATA = "SELECT EVENT_DATA,EVENT_NAME,VERSION FROM THR_ONBOARD_AGGREGATE_EVENT WHERE AGGREGATE_ID=? ORDER BY CREATED_DATE";
	public static final String ASSOCIATE = "Associate";

}
