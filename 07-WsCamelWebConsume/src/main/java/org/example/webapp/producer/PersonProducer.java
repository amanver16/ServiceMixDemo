package org.example.webapp.producer;

import java.io.IOException;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.converter.stream.CachedOutputStream;
import org.example.webapp.bean.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonProducer {
	@Produce(uri = "seda:addPerson")
    ProducerTemplate addPersonTemplate;
	
	@Produce(uri = "direct:getPerson")
    ProducerTemplate getPersonTemplate;
	
	@Produce(uri = "seda:deletePerson")
    ProducerTemplate deletePersonTemplate;
	
	public void addPerson(String msg){
		System.out.println("Producer send body to seda:sendMessage : "+msg);
		addPersonTemplate.sendBody(msg);
	}
	
	public String viewPerson(Object msg) throws IOException{
		return getPersonTemplate.requestBody(msg,String.class);
	}
	
	public void deletePerson(String msg){
		deletePersonTemplate.sendBody(msg);
	}
	
	
}
