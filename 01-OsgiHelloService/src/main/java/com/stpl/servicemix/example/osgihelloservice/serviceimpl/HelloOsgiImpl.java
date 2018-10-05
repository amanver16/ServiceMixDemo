package com.stpl.servicemix.example.osgihelloservice.serviceimpl;

import com.stpl.servicemix.example.osgihelloservice.service.HelloOsgi;

public class HelloOsgiImpl implements HelloOsgi {

	public String sayHello(String context) {
		return "Hello "+context;
	}
	
	public String sayHello() {
		return "Hello Osgi";
	}

}
