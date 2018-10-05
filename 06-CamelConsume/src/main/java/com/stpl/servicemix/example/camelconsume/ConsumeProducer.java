package com.stpl.servicemix.example.camelconsume;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConsumeProducer {
	@Produce(uri = "seda:sendMessage")
    ProducerTemplate producerTemplate;
	
	public void sendMeaasge(String msg){
		System.out.println("Producer send body to seda:sendMessage : "+msg);
		producerTemplate.sendBody(msg);
	}
}
