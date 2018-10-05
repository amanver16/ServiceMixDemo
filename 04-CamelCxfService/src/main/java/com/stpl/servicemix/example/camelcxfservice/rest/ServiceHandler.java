
package com.stpl.servicemix.example.camelcxfservice.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.stpl.servicemix.example.camelcxfservice.rest.model.Person;


public class ServiceHandler {

    private Map<Integer, Person> persons = new HashMap<Integer, Person>();

    public void init(){
        add(new Person(0, "Test Person", 100));
    }

    private Response add(Person person){
        persons.put(person.getId(), person);
        
        return Response.created(URI.create("/personservice/person/get/"
                + person.getId())).build();
    }

    private Person get(int id){
        Person person = persons.get(id);
        if (person == null) {
            ResponseBuilder builder = Response.status(Status.OK);
            builder.entity("Person with ID " + id + " not found.");
            throw new WebApplicationException(builder.build());
        }

       return person;
    }

    private void delete(int id){
        if (persons.remove(id) == null) {
            ResponseBuilder builder = Response.status(Status.OK);
            builder.entity("Person with ID " + id + " not found.");
            throw new WebApplicationException(builder.build());
        }
    }

    public Person getPerson(Person person){
    	System.out.println("Enter to getPerson : ");
        return get(person.getId());
    }

    public Response putPerson(Person person){
        return add(person);
    }

    public void deletePerson(Person person){
        delete(person.getId());
    }
    
    public Person getPersonTest(String id){
    	System.out.println("Enter to getPerson : ");
        return get(Integer.parseInt(id));
    }

}
