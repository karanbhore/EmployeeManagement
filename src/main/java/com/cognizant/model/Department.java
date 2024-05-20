package com.cognizant.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {
    
	@Schema(name = "Department_ID", example = "2", required = true)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank(message= "Department name is mandatory")
//	@Size(min=2, max=100, message="Employee Department")
	@Schema(name = "Department_Name", example = "Dev", required = true)
    private String departmentName;

//    @JsonManagedReference
    @ManyToMany(mappedBy = "departments")
    private List<Employee> employees;
}