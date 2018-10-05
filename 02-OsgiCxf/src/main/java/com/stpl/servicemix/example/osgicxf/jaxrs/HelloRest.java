package com.stpl.servicemix.example.osgicxf.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/hellorest/")
public class HelloRest {
	@GET
    @Path("/gethello/{name}/")
    @Produces("application/json")
    public String getHello(@PathParam("name") String name) {
        System.out.println("----invoking getHello, name is: " + name);
        return "Hello "+name;
    }

    
}
