package com.cognizant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cognizant.model.Address;
import com.cognizant.model.Employee;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testSaveAndFindByEmployeeName() {
		// Create an Employee entity
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		employee.setGender("Male");
		employee.setDesignation("Software Engineer");

		// Save the employee to the database
		Employee savedEmployee = employeeRepository.save(employee);

		// Retrieve the employee by name
		List<Employee> retrievedEmployees = employeeRepository.findByEmployeeName("John Doe");

		// Assert that the retrieved employee matches the saved employee
		assertThat(retrievedEmployees).isNotEmpty();
		assertThat(retrievedEmployees.get(0).getEmployeeName()).isEqualTo(savedEmployee.getEmployeeName());
	}

	@Test
	public void testFindAll() {
		// Create an Employee entity
		Employee employee1 = new Employee();
		employee1.setEmployeeName("John Doe");
		employee1.setGender("Male");
		employee1.setDesignation("Software Engineer");

		Employee employee2 = new Employee();
		employee2.setEmployeeName("Jane Smith");
		employee2.setGender("Female");
		employee2.setDesignation("Project Manager");

		// Save the employees to the database
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);

		// Retrieve all employees
		List<Employee> employees = employeeRepository.findAll();

		// Assert that all employees are retrieved
		assertThat(employees).hasSize(2);
	}

	@Test
	public void testFindByAddressesCity() {
		// Create an Employee entity with an Address
		Employee employee = new Employee();
		employee.setEmployeeName("John Doe");
		employee.setGender("Male");
		employee.setDesignation("Software Engineer");

		Address address = new Address();
		address.setCity("New York");
		address.setEmployee(employee); // Set the association

		employee.setAddresses(List.of(address));

		// Save the employee and address to the database
		employeeRepository.save(employee);

		// Retrieve employees by city
		List<Employee> employees = employeeRepository.findByAddresses_City("New York");

		// Assert that the retrieved employee matches the saved employee
		assertThat(employees).isNotEmpty();
		assertThat(employees.get(0).getEmployeeName()).isEqualTo("John Doe");
		assertThat(employees.get(0).getAddresses().get(0).getCity()).isEqualTo("New York");
	}
}
