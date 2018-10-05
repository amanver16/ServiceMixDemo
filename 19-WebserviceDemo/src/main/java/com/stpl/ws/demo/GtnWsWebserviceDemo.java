package com.stpl.ws.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GtnWsWebserviceDemo {

	public GtnWsWebserviceDemo() {
		System.out.println("inside constructor");
	}

	private GtnTestService gtnTestService;

	public GtnTestService getGtnTestService() {
		return gtnTestService;
	}

	public void setGtnTestService(GtnTestService gtnTestService) {
		this.gtnTestService = gtnTestService;
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String test() {
		System.out.println("Test Service-----------" + gtnTestService.getValue());
		return "Success";
	}
}
