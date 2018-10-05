package com.stpl.servicemix.example.osgihellotracker.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.stpl.servicemix.example.osgihelloservice.service.HelloOsgi;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		System.out.println("Enter to start Tracker Bundle");
		ServiceTracker<HelloOsgi,HelloOsgi> tracker = new ServiceTracker<HelloOsgi, HelloOsgi>(context, HelloOsgi.class, null);
		tracker.open();
		HelloOsgi hello = tracker.getService();
		tracker.close();
		if (hello != null) {
			System.out.println(hello.sayHello("World"));
		}else {
			System.out.println("Hello Service is not available");
		}

	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("Tracker Bundle Stoped");
	}

}
