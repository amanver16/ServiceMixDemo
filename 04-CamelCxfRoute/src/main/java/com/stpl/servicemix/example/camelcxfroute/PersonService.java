package com.stpl.servicemix.example.camelcxfroute;


import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.stpl.servicemix.example.camelcxfservice.rest.model.Person;

// This could be an interface if CAMEL-6014 is fixed.

@Path("/personservice/")
public class PersonService {
    
    @GET
    @Path("/person/getPersonTest/{id}")
    @Produces("application/json")
    public Person getPersonTest(String id) {
        return null;
    }

    @POST
    @Path("/person/post")
    public Response putPerson(Person person) {
        return null;
    }

    @POST
    @Path("/person/delete")
    public void deletePerson(Person person) {
    	
    }
    
    @POST
    @Path("/person/get")
    @Produces("application/json")
    public Person getPerson(Person person) {
        return null;
    }
}
