package com.cognizant.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.model.Department;
import com.cognizant.model.Employee;
import com.cognizant.repository.DepartmentRepository;
import com.cognizant.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testSaveOrUpdateEmployee() {
        // Create mock employee and department
        Department department = new Department();
        Employee employee = new Employee(1L, "John", "Male", "Developer", null, Arrays.asList(department));

        // Mock behavior of departmentRepository
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Mock behavior of employeeRepository
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the method under test
        Employee savedEmployee = registrationService.saveOrUpdateEmployee(employee);

        // Assert the result
        assertEquals(employee, savedEmployee);
    }

}
