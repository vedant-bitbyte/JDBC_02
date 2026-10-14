package com.jdbc.employeepojo;

public class EmployeeDTO { 
	//DTO -> Data Transfer Object
	// This consists of the fields that a we will be inserting into a table
	
	private String name;
	private String password;
	private int salary;
	
	public EmployeeDTO(String name, String password, int salary) {
		super();
		this.name = name;
		this.password = password;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [name=" + name + ", salary=" + salary + "]";
	}
	
	
}
