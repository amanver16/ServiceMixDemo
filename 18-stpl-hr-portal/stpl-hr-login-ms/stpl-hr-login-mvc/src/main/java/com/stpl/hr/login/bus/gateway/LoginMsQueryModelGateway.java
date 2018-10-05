package com.stpl.hr.login.bus.gateway;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import com.stpl.hr.login.domain.query.data.bean.LoginMsUserQueryBean;

@Component
public class LoginMsQueryModelGateway {

	@Produce(uri = "direct-vm:getAggregateIdByUserName")
	ProducerTemplate loginMsQueryConsumer;

	public String getAggregateIdByUserName(String userName) {

		Object response = loginMsQueryConsumer.requestBody(userName);
		if (response == null) {
			System.out.println("LoginMs : MVC Layer: no user found .");
			return null;
		}
		LoginMsUserQueryBean queryBean = (LoginMsUserQueryBean) response;
		System.out.println("LoginMs : MVC Layer: Received AggregateId " + queryBean.getAggregateId());
		return queryBean.getAggregateId();
	}

}
