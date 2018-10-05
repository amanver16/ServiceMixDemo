package com.stpl.servicemix.example.camelconsume;

import org.springframework.stereotype.Component;

@Component
public class DisplayMessage {

	
	public void displayMessage(String message){
		System.out.println(message);
	}
}
