package com.stpl.servicemix.example.osgihelloservice.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.stpl.servicemix.example.osgihelloservice.service.HelloOsgi;
import com.stpl.servicemix.example.osgihelloservice.serviceimpl.HelloOsgiImpl;

public class Activator implements BundleActivator {

	private HelloOsgi service = null;
	private ServiceRegistration<HelloOsgi> registration;
 	public void start(BundleContext context) throws Exception {
 		service = new HelloOsgiImpl();
		registration = context.registerService(HelloOsgi.class, service, null);
	}

	public void stop(BundleContext context) throws Exception {
		
		registration.unregister();
	}

}
