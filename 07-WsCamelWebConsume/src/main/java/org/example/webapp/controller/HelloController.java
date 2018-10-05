package org.example.webapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.webapp.bean.Person;
import org.example.webapp.producer.PersonProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HelloController {

	@Autowired
	private PersonProducer personProducer;
	
	@RequestMapping(value = "/addPerson.htm", method = RequestMethod.GET)
	public String addPerson(HttpServletRequest request, HttpServletResponse response) {
		try{
			System.out.println(request.getParameter("id"));
			Person person=new Person();
			person.setAge(Integer.parseInt(request.getParameter("age")));
			person.setId(request.getParameter("id"));
			person.setName(request.getParameter("name"));
			personProducer.addPerson(person.getXML());
			request.setAttribute("message", "Add Person");
			request.getRequestDispatcher("Success.jsp").forward(request, response);
		} catch (Exception e) {
			try {
				request.setAttribute("message", "Delete Person");
				request.getRequestDispatcher("Failed.jsp").forward(request, response);
			} catch (Exception e1) {
				
			}
		} 
		
		return "Success";
	}
	
	@RequestMapping(value = "/viewPerson.htm", method = RequestMethod.GET)
	public void viewPerson(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println(request.getParameter("id"));
		Person person=new Person();
		person.setId(request.getParameter("id"));
		String result=null;
		try {
			result = personProducer.viewPerson(person.getXML());
			System.out.println(result);
			ObjectMapper mapper=new ObjectMapper();
			JsonNode person1=mapper.readTree(result).get("person");
			System.out.println("name"+ person1.get("name").asText());
			request.setAttribute("id", person1.get("id"));
			request.setAttribute("personName", person1.get("name").asText());
			request.setAttribute("age", person1.get("age"));
			request.getRequestDispatcher("PersonView.jsp").forward(request, response);
		} catch (Exception e) {
			try {
				request.setAttribute("message", "View Person");
				request.getRequestDispatcher("Failed.jsp").forward(request, response);
			} catch (Exception e1) {
				
			}
		} 
	}
	
	@RequestMapping(value = "/deletePerson.htm", method = RequestMethod.GET)
	public String deletePerson(HttpServletRequest request, HttpServletResponse response) {
		try{
			System.out.println(request.getParameter("id"));
			Person person=new Person();
			person.setId(request.getParameter("id"));
			personProducer.deletePerson(person.getXML());
			request.setAttribute("message", "Delete Person");
			request.getRequestDispatcher("Success.jsp").forward(request, response);
		} catch (Exception e) {
			try {
				request.setAttribute("message", "Delete Person");
				request.getRequestDispatcher("Failed.jsp").forward(request, response);
			} catch (Exception e1) {
				
			}
		} 
		
		return "Success";
	}
}
