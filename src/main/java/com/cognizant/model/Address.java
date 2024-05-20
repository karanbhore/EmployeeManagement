package com.cognizant.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
    
	@Schema(name = "Address_ID", example = "2", required = true)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message= "Street name is mandatory")
	@Schema(name = "Address_Street", example = "HinjewadiRoad", required = true)
	private String street;
	
	@NotBlank(message= "Pin is mandatory")
	@Schema(name = "Address_Pin", example = "416221", required = true)
    private String pincode;
	
	@NotBlank(message= "Landmark is mandatory")
	@Schema(name = "Address_landmark", example = "LaxmiChowk", required = true)
    private String landmark;
	
	@NotBlank(message= "City is mandatory")
	@Schema(name = "Address_city", example = "Pune", required = true)
    private String city;
	
	@NotBlank(message= "State is mandatory")
	@Schema(name = "Address_State", example = "Maharashtra", required = true)
    private String state;

	
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}