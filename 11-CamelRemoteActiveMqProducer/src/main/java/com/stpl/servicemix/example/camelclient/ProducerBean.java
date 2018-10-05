package com.stpl.servicemix.example.camelclient;


public class ProducerBean {

	private int messageCount = 0;

	public String produce(Object input) {
		System.out.println("Producer Send : "+messageCount);
		return "Message Count = " + (messageCount++);
	}

}
