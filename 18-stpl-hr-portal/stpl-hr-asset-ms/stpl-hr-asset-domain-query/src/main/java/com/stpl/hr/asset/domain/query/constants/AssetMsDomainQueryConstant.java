package com.stpl.hr.asset.domain.query.constants;

public class AssetMsDomainQueryConstant {
	private AssetMsDomainQueryConstant() {
		super();
	}

	public static final String ASSET_INVENTRY_INSERT_QUERY = "INSERT INTO THR_ASSET_QUERY_ALLOCATION (ASSET_ID, AGGREGATE_ID, ASSET_TYPE, SPECIFICATION, ALLOCATION_STATUS, ASSET_OWNER, ASSET_OWNER_ID, START_DATE, END_DATE) VALUES (?,?,?,?,?,?,?,?,?);";
	public static final String ASSET_INVENTRY_SELECT_ALL_QUERY = "SELECT SYSTEM_ID, ASSET_ID, AGGREGATE_ID, ASSET_TYPE, SPECIFICATION, ALLOCATION_STATUS, ASSET_OWNER, START_DATE, END_DATE FROM THR_ASSET_QUERY_ALLOCATION;";
	public static final String ASSET_SELECT_VACANT_ASSET = "SELECT TOP 1 AGGREGATE_ID FROM THR_ASSET_QUERY_ALLOCATION WHERE ALLOCATION_STATUS='VACANT' AND ASSET_TYPE=?;";
	public static final String ASSET_INVENTRY_SELECT_QUERY_BY_AGGREAGETID = "SELECT SYSTEM_ID, ASSET_ID, AGGREGATE_ID, ASSET_TYPE, SPECIFICATION, ALLOCATION_STATUS, ASSET_OWNER, START_DATE, END_DATE FROM THR_ASSET_QUERY_ALLOCATION WHERE AGGREGATE_ID=?;";
	public static final String ASSET_ALLOCATE_QUERY = "UPDATE THR_ASSET_QUERY_ALLOCATION SET ALLOCATION_STATUS=?, ASSET_OWNER=?, ASSET_OWNER_ID=?, START_DATE=? WHERE ASSET_ID=?;";
	public static final String DUPLICATE_ASSET_QUERY = "SELECT COUNT(ASSET_ID) FROM THR_ASSET_QUERY_ALLOCATION WHERE  ASSET_ID=?;";

}
