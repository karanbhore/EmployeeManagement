package com.cognizant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cognizant.model.Department;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DepartmentRepositoryTests {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSaveAndFindByDepartmentName() {
        // Create a Department entity
        Department department = new Department();
        department.setDepartmentName("IT");

        // Save the department to the database
        Department savedDepartment = departmentRepository.save(department);

        // Retrieve the department by its name
        Department retrievedDepartment = departmentRepository.findByDepartmentName("IT");

        // Assert that the retrieved department matches the saved department
        assertThat(retrievedDepartment).isNotNull();
        assertThat(retrievedDepartment.getDepartmentName()).isEqualTo(savedDepartment.getDepartmentName());
    }
}
