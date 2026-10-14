package com.jdbc.crudapplication;

import java.util.Scanner;

import com.jdbc.connectionandqueries.QueryRun;
import com.jdbc.employeepojo.EmployeeDTO;

public class CrudOperations {
	
	private final int INSERT = 1;
	private final int READ = 2;
	private final int UPDATE = 3;
	private final int DELETE = 4;
	private final int EXIT = 5;
	
	private static int MAX_ATTEMPTS = 5;
	
	// private int empId;
	private String empName;
	private String empPassword;
	private int empSalary;
	
	private QueryRun queryRun;
	
	public CrudOperations() {
		queryRun = new QueryRun();  
		//-> Composition
		// In JDBCCrudMain class we are creating the CrudOperations object, then in its constructor 
		// we are creating the QueryRun object, this is Composition (Strong Association).
		// We are not externally passing any object to CrudOperations object.
	}
	
	public void doStart() {

		Scanner sc = new Scanner(System.in);
		int attempts = 0;
		
		while (true) {
			System.out.println("\n\n====Menu====");
			System.out.println("1. Insert");
			System.out.println("2. Read");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit\n\n");
			System.out.print("Enter your choice: ");
			int choice = 0;
			
			try {
				choice = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				System.out.println("Invalid Input!! Do choice from menu.");
				attempts++;
				// We had added an attempts limit that is set to 3. If it exceeds then we exit the code.
				
				if (attempts >= MAX_ATTEMPTS) { // MAX_ATTEMPTS = 5
					System.out.println("You have reached limit.");
					sc.close();
					return;
				}
				
				continue;
			}
			
			if (choice < 1 || choice > 5) {
				System.out.println("Invalid Input!! Do choice between 1 to 5.");
				attempts++;
				
				if (attempts >= MAX_ATTEMPTS) {
					System.out.println("You have reached limit.");
					sc.close();
					return;
				}
			}
			
			switch (choice) {
			case INSERT:
				System.out.println("\n====INSERT DATA====");
				
				System.out.print("Enter Name: ");
				empName = sc.nextLine();
				
				System.out.print("Enter Password: ");
				empPassword = sc.nextLine();
				
				System.out.print("Enter Salary: ");
				empSalary = sc.nextInt();
				sc.nextLine();
				
				EmployeeDTO employeeDTO = new EmployeeDTO(empName, empPassword, empSalary);
				
				queryRun.insert(employeeDTO);
				// We pass the EmployeeDTO object to the QueryRun object.
				// This is aggregation.
				// When we are externally passing an EmployeeDTO object to the queryRun object. (Weak Association)
				
				break;
				
			case READ:
				System.out.println("\n====READ DATA====");
				
				System.out.print("Enter Name: ");
				empName = sc.nextLine();
				
				System.out.print("Enter Password: ");
				empPassword = sc.nextLine();
				
				queryRun.read(empName, empPassword);
				break;
				
			case UPDATE:
				System.out.println("\n====UPDATE DATA====");
				
				System.out.print("Enter Name: ");
				empName = sc.nextLine();
				
				System.out.print("Enter Updated Salary: ");
				empSalary = sc.nextInt();
				sc.nextLine();
				
				queryRun.update(empName, empSalary);
				break;
				
			case DELETE:
				System.out.println("\n====DELETE DATA====");
				
				System.out.print("Enter Name: ");
				empName = sc.nextLine();
				
				queryRun.delete(empName);
				break;
				
			case EXIT:
				System.out.println("Shut Down...");
				sc.close();
				return;
			}
		}
	}
}
