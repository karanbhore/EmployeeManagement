package com.cognizant.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.cognizant.model.Employee;
import com.cognizant.service.RegistrationService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployeeRegistration.class)
public class EmployeeRegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @InjectMocks
    private EmployeeRegistration employeeRegistration;

    @Test
    public void testRegisterEmployee() throws Exception {
        Employee employee = new Employee(1L, "John", "Male", "Developer", null, null);
        when(registrationService.saveOrUpdateEmployee(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder requestBuilder = post("/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"employeeName\": \"John\", \"gender\": \"Male\", \"designation\": \"Developer\" }");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeName").value("John"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.designation").value("Developer"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = new Employee(1L, "John", "Male", "Developer", null, null);
        Employee employee2 = new Employee(2L, "Alice", "Female", "Manager", null, null);
        when(registrationService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/getAllEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeName").value("John"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].designation").value("Developer"))
                .andExpect(jsonPath("$[1].employeeName").value("Alice"))
                .andExpect(jsonPath("$[1].gender").value("Female"))
                .andExpect(jsonPath("$[1].designation").value("Manager"));
    }

    @Test
    public void testGetEmployeesByName() throws Exception {
        Employee employee1 = new Employee(1L, "John", "Male", "Developer", null, null);
        when(registrationService.getEmployeesByName("John")).thenReturn(Collections.singletonList(employee1));

        mockMvc.perform(get("/byName?name=John"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeName").value("John"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].designation").value("Developer"));
    }

    @Test
    public void testGetEmployeesByDepartment() throws Exception {
        Employee employee1 = new Employee(1L, "John", "Male", "Developer", null, null);
        when(registrationService.getEmployeesByDepartment(anyString())).thenReturn(Collections.singletonList(employee1));

        mockMvc.perform(get("/byDepartment?departmentName=IT"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeName").value("John"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].designation").value("Developer"));
    }

    @Test
    public void testGetEmployeesByCity() throws Exception {
        Employee employee1 = new Employee(1L, "John", "Male", "Developer", null, null);
        when(registrationService.getEmployeesByCity(anyString())).thenReturn(Collections.singletonList(employee1));

        mockMvc.perform(get("/byCity?city=New York"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].employeeName").value("John"))
                .andExpect(jsonPath("$[0].gender").value("Male"))
                .andExpect(jsonPath("$[0].designation").value("Developer"));
    }

}
