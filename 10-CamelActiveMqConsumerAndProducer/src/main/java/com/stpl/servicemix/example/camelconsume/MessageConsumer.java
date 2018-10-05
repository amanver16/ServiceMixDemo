package com.stpl.servicemix.example.camelconsume;

public class MessageConsumer {
	public void consume(Object body) {
         System.out.println("Consumed Message : "+body);
    }
}
