package com.stpl.hr.onboard.bus.gateway;

import java.util.List;
import java.util.UUID;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.onboard.domain.query.data.bean.OnboardMsOnboardAssociateBean;

@Component
public class OnboardMsQueryModelGateway {
	
	@Produce(uri = "direct-vm:getAllAggregate")
	ProducerTemplate queryAllAggregate;

	@Produce(uri = "direct-vm:getAggregateIdByAssociateId")
	ProducerTemplate onboardMsQueryConsumer;

	@Produce(uri = "direct-vm:getLatestAssociateId")
	ProducerTemplate onboardMsQueryLatestAssociateId;

	@SuppressWarnings("unchecked")
	public List<OnboardMsOnboardAssociateBean> getAllAssociate() {
		Object response = queryAllAggregate.requestBody("");
		List<OnboardMsOnboardAssociateBean> associateList = (List<OnboardMsOnboardAssociateBean>) response;
		return associateList;
	}

	public String getAggregateIdByAssociateId(String associateId) {

		if (associateId == null) {
			String newAggregateId = UUID.randomUUID().toString();
			System.out.println("Onboard MS MVC Layer: new associate found . New AggregateId " + newAggregateId);
			return newAggregateId;
		}
		Object response = onboardMsQueryConsumer.requestBody(associateId);
		OnboardMsOnboardAssociateBean queryBean = (OnboardMsOnboardAssociateBean) response;
		System.out.println("Onboard MS MVC Layer : Received AggregateId " + queryBean.getAggregateId());
		return queryBean.getAggregateId();
	}

	public String getLatestAssociateId() {
		Object response = onboardMsQueryLatestAssociateId.requestBody("");
		String latestAssociateId = (String) response;
		System.out.println("Onboard MS MVC Layer: Received Latest AssociateId " + latestAssociateId);
		return latestAssociateId;
	}
}
