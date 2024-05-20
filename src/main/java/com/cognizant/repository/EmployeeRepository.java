package com.cognizant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByEmployeeName(String name);
	List<Employee> findAll();
//	List<Employee> findByDepartmentName(String departmentName);
	   List<Employee> findByAddresses_City(String city);

	
}
