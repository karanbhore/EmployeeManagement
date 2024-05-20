package com.cognizant.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {

	@Schema(name = "Employee_ID", example = "2", required = true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message= "Employee name is mandatory")
	@Size(min=2, max=100, message="Employee name")
	@Schema(name = "Employee_name", example = "Raj", required = true)
	private String employeeName;

	@NotBlank(message= "Employee gender is mandatory")
	@Schema(name = "Employee_gender", example = "Male", required = true)
	private String gender;

	@NotBlank(message= "Employee designation is mandatory")
	@Schema(name = "Employee_designation", example = "Associate", required = true)
	private String designation;

	
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Address> addresses;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "employee_department", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
	private List<Department> departments;
}