package com.jmx.shiv;

public class TestInterface implements TestInterfaceMBean {

	private int age;
	private String name;
	
	
	public TestInterface(int defaultAge, String defaultName) {
		this.age=defaultAge;
		this.name=defaultName;	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String checkDetails() {
		return "My Name is="+this.name+" and my age is="+this.age;
	}
	
	

}
