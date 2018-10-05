package com.stpl.servicemix.example.camelconsume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumeBean {
	
	@Autowired
	private ConsumeProducer consumeProducer;

	public void  camelConsume(Object input){
		System.out.println("Camel Consume executed");
		consumeProducer.sendMeaasge("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
				"<person>\n" +
				"    <age>21</age>\n" +
				"    <id>7</id>\n" +
				"    <name>John Smithddddddd</name>\n" +
				"</person>");
	}

	public ConsumeProducer getConsumeProducer() {
		return consumeProducer;
	}

	public void setConsumeProducer(ConsumeProducer consumeProducer) {
		this.consumeProducer = consumeProducer;
	}
	
	
}
