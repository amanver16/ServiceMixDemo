package org.example.webapp.bean;

public class Person {

	private String id;
	private String name;
	private int age;
	
	public Person(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String getXML(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
				"<person>\n" +
				"    <age>"+age+"</age>\n" +
				"    <id>"+id+"</id>\n" +
				"    <name>"+name+"</name>\n" +
				"</person>";
	}

}
