package com.stpl.hr.onboard.domain.aggregate.associate.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class OnboardMsAssociateService {

	@Produce(uri = "direct-vm:getOnboardLatestAssociateId")
	ProducerTemplate queryLatestAssociateId;

	public String generateAssociateId() {
		long latestAssociateId = (Long) queryLatestAssociateId.requestBody("");
		System.out.println("OnboardMS: OnboardMsAssociateService: latestAssociateId:" + latestAssociateId);
		if (latestAssociateId == 0L) {
			latestAssociateId = 10000L;
		}
		String generatedAssociateId = String.valueOf(++latestAssociateId);
		System.out.println("OnboardMS: OnboardMsAssociateService: generatedAssociateId:" + generatedAssociateId);
		return generatedAssociateId;
	}
}
