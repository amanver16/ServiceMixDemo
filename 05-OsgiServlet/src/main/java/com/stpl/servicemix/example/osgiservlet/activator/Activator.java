package com.stpl.servicemix.example.osgiservlet.activator;

import javax.servlet.ServletException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.stpl.servicemix.example.osgiservlet.servlet.HelloServlet;


public class Activator implements BundleActivator {
	private ServiceReference<HttpService> realServiceReference;
	private HttpService httpService;
	public void start(BundleContext context) throws Exception {
		realServiceReference = context.getServiceReference(HttpService.class);
        httpService = context.getService(realServiceReference);
        try {
            httpService.registerServlet("/osgiServlet", new HelloServlet(),null, null);
            httpService.createDefaultHttpContext();
            
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (NamespaceException e) {
            e.printStackTrace();
        }
	}

	public void stop(BundleContext context) throws Exception {
		httpService.unregister("/osgiServlet");
	}

}
