package com.stpl.hr.onboard.domain.query.handler;

import java.sql.SQLException;
import java.util.List;

import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;
import com.stpl.hr.onboard.domain.query.mongo.service.OnboardMSDomainQueryMongoService;

public class OnboardMSDomainQueryGatewayHandler {

	private OnboardMSDomainQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(OnboardMSDomainQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public OnboardMsOnboardAssociateBean getAssociateDetails(String associateId) throws SQLException {
		return domainQueryDbSevice.getAssociateDetails(associateId);
	}

	public List<OnboardMsOnboardAssociateBean> getAllAssociateDetails() throws SQLException {
		return domainQueryDbSevice.getAllAssociateDetails();
	}

	public long getLatestAssociateId() throws SQLException {
		return domainQueryDbSevice.getLatestAssociateId();
	}
}
