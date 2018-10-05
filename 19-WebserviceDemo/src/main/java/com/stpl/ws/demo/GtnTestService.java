package com.stpl.ws.demo;

public class GtnTestService {
	public int value = 0;

	public GtnTestService() {
		System.out.println("GtnTestService...");
	}

	public void setValue(int value) {

		System.out.println("testing...");
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
