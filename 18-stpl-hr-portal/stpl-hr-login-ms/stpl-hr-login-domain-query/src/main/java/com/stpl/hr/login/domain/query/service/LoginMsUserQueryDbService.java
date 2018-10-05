package com.stpl.hr.login.domain.query.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.stpl.hr.login.domain.query.data.bean.LoginMsUserQueryBean;

public class LoginMsUserQueryDbService {
	private DataSource queryDataSource;

	public LoginMsUserQueryDbService() {
	}

	public void setQueryDataSource(DataSource queryDataSource) {
		this.queryDataSource = queryDataSource;
	}

	public void create() throws SQLException {
		try (Connection connection = queryDataSource.getConnection();
				Statement sta = connection.createStatement();
				ResultSet resultSet = sta.executeQuery("SELECT count(*) FROM dbo.THR_LOGIN_QUERY_USER;");) {
			if (resultSet.next()) {
				System.out.println("User listtttttttttttttttttttt---------" + resultSet.getInt(1));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public LoginMsUserQueryBean getAggregateIdByUserName(String userName) throws SQLException {
		LoginMsUserQueryBean resultBean = null;
		try (Connection connection = queryDataSource.getConnection();
				Statement sta = connection.createStatement();
				ResultSet resultSet = sta
						.executeQuery("SELECT AGGREGATE_ID,UserName FROM dbo.THR_LOGIN_QUERY_USER where UserName='"
								+ userName + "';");) {
			if (resultSet.next()) {
				resultBean = new LoginMsUserQueryBean();
				resultBean.setAggregateId(resultSet.getString(1));
				resultBean.setUserName(resultSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultBean;
	}

	public void addUser(LoginMsUserQueryBean inputUserBean) throws SQLException {

		try (Connection connection = queryDataSource.getConnection(); Statement sta = connection.createStatement()) {
			String query = "INSERT into dbo.THR_LOGIN_QUERY_USER(AGGREGATE_ID,USERNAME) values('"
					+ inputUserBean.getAggregateId() + "','" + inputUserBean.getUserName() + "');";
			sta.executeUpdate(query);

			connection.commit();

		}
	}

}