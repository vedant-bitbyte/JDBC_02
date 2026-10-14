package com.jdbc.connectionandqueries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jdbc.employeepojo.EmployeeDTO;

public class QueryRun {
	
	public QueryRun() {
		createDB();
		createTable();
	}
	
	private void createDB() { 
		// Create the Database when the QueryRun object is created in CrudOperations class's constructor
		Connection connection = null;
		Statement statement = null;
		
		try {
			
			connection = ConnectionFactory.getConnection();
			
			String sqlQuery = "CREATE DATABASE IF NOT EXISTS JDBC01;";
			
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			
		}
		System.out.println("Database Created.");
		
	}
	
	private void createTable() {	// Table columns: emp_id	emp_name	password	emp_salary
		Connection connection = null;
		Statement statement = null;
		
		try {
			
			connection = ConnectionFactory.getConnection(); 
			//-> This just manages the connection with the DB, not the queries which we will fire
			
			String sqlQuery = "CREATE TABLE IF NOT EXISTS employee(emp_id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "emp_name VARCHAR(30),"
					+ "password VARCHAR(30),"
					+ "emp_salary INT);";
			
			statement = connection.createStatement();	
			//-> The statement object is in the connection object
			//-> The statement is used to fire static query that is given by the programmer.
			// It does not take any external values.
			
			statement.executeUpdate(sqlQuery);	
			//-> The executeUpdate() is used to fire the query with no data returned.
			// This method returns an int value: showing how many rows are affected.
			// Used for CREATE, INSERT, UPDATE, DELETE type queries, that does not return any data.
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			// Both the objects are passed to the ConnectionFactory class's close(), which uses the AutoCloseable interface
			// as its parameter. AutoCloseable is the Parent interface of both the Connection and Statement interface.
			
		}
		
		System.out.println("Table Created.");
		
	}
	
	public void insert(EmployeeDTO employeeDTO) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			String sqlQuery = "INSERT INTO employee(emp_name, password, emp_salary) VALUES "
					+ "('" + employeeDTO.getName() + "', '" + employeeDTO.getPassword() + "', " + employeeDTO.getSalary() + ");";
			
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			// This uses Statement interface which is used to fire static queries i.e the queries programmed by the programmer
			// But the String sqlQuery is created at the runtime with the help of employeeDTO object's values. 
			// But this still is the static query, which leads to SQL Injection threat.
			// SQL Injection -> When user input is directly added to a Statement SQL query, 
			// an attacker can insert malicious SQL code and manipulate the database.
			
			System.out.println("Data Inserted.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			
		}
	}
	
	public void read(String name, String password) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			String sqlQuery = "SELECT * FROM employee "
					+ "WHERE emp_name='" + name + "' AND password='" + password + "';";
			// This String is formed at the time of runtime by concatenating the strings, so this leads to threat of SQL Injection.
			/* 
				====READ DATA====
				Enter Name: Bhavesh Bhadane
				Enter Password: anything'OR'1'='1
				Emp ID: 1
				Emp Name: Bhavesh Bhadane
				Emp Password: 12345
				Emp Salary: 1000.
			 */
			// Now the manipulation of the password leads to form the sqlQuery string to such that
			// SELECT * FROM employee WHERE emp_name='Bhavesh Bhadane' AND password='anything'OR'1'='1';
			// Now the password and the '1'='1' are in OR, so this leads to true. Due to this the query fetches the data.
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			// When this query is executed, the resultSet object is created but no data is fetched and stored in it.
			// It just have the MetaData i.e. it points towards the first row i.e. columns.
			// This is Cursor based.
			
			if (resultSet.next()) {
				// The .next() is the method which then fetch the single row's data and stored in resultSet object, one at a time.
				// The next time .next() gives us second row's data.
				// resultSet.next() return us a boolean. true -> if row is fetched. false -> not fetched.
				// We could also use while loop to get all the rows, but here we are just fetching a single users data, so if is fine.
				
				int getEmp_id = resultSet.getInt("emp_id");
				String getEmp_name = resultSet.getString("emp_name");
				String getPassword = resultSet.getString("password");
				int getEmp_salary = resultSet.getInt("emp_salary");
				
				System.out.println("Emp ID: " + getEmp_id + "\n"
								+ "Emp Name: " + getEmp_name + "\n"
								+ "Emp Password: " + getPassword + "\n"
								+ "Emp Salary: " + getEmp_salary + ".\n");
				

				System.out.println("Data Fetched.");
				
			} else {
				System.out.println("USER NOT FOUND\n");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(resultSet);
			// The Parent of ResultSet interface is also the AutoCloseable interface. 
			// So we had already created a close() method in ConnectionFactory class, with the AutoCloseable interface, so that every
			// child of it can we closed using the same close() method.
			
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			
		}
	}
	
	public void update(String name, int salary) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			String sqlQuery = "UPDATE employee SET emp_salary=" + salary + " WHERE emp_name='" + name + "';";
			
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
			
			System.out.println("Data Updated.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			
		}
	}
	
	public void delete(String name) {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = ConnectionFactory.getConnection();
			
			String sqlQuery = "DELETE FROM employee WHERE emp_name='" + name + "';";
			
			statement = connection.createStatement();
			int executeUpdate = statement.executeUpdate(sqlQuery);
			
			if(executeUpdate >= 0) {
				System.out.println("Data Deleted.");
				
			} else {
				System.out.println("USER NOT FOUND.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
			
		}
	}
}
