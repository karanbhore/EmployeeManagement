package com.cognizant.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.model.Address;
import com.cognizant.model.Department;
import com.cognizant.model.Employee;
import com.cognizant.service.RegistrationService;
import com.itextpdf.io.exceptions.IOException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@Tag(name = "Employee", description = "EmployeeManagement")

@RestController
public class EmployeeRegistration {

	@Autowired(required = true)
	RegistrationService registrationService;

	@Operation(summary = "SaveEmployee", method = "registerEmployee", description = "Gets the list of all Employees by City!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Error while resgistering.", content = {
					@Content(schema = @Schema()) }) })

	@PostMapping("/save")
	public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
		return ResponseEntity.ok(registrationService.saveOrUpdateEmployee(employee));
	}

	@Operation(summary = "fetchAllEmployees", method = "getAllEmployees", description = "Gets the list of all Employees!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Error while fetching the List.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(registrationService.getAllEmployees());
	}

	@Operation(summary = "fetchByName", method = "getEmployeesByName", description = "Gets the list of all Employees by name!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Error while fetching the List.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/byName")
	public ResponseEntity<List<Employee>> getEmployeesByName(@RequestParam String name) {
		return ResponseEntity.ok(registrationService.getEmployeesByName(name));
	}

	@Operation(summary = "fetchByDepartment", method = "getEmployeesByDepartment", description = "Gets the list of all Employees by Department!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Department.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Error while fetching the List.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/byDepartment")
	public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam String departmentName) {
		return ResponseEntity.ok(registrationService.getEmployeesByDepartment(departmentName));
	}

	@Operation(summary = "fetchByCity", method = "getEmployeesByCity", description = "Gets the list of all Employees by City!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Address.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Error while fetching the List.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/byCity")
	public ResponseEntity<Object> getEmployeesByCity(@RequestParam String city) {
		return ResponseEntity.ok(registrationService.getEmployeesByCity(city));
	}

	@Operation(summary = "download_PDF_report", method = "downloadPdfReport", description = "Gets the list of all Employees in pdf form!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = ".pdf") }),
			@ApiResponse(responseCode = "404", description = "Error while downloading.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/report/pdf")
	public ResponseEntity<byte[]> downloadPdfReport() {
		List<Employee> employees = registrationService.getEmployeesByName(""); // Fetch all employees
		ByteArrayInputStream in = registrationService.createPdfReport();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=employees.pdf");
			headers.setContentType(MediaType.APPLICATION_PDF);

			return ResponseEntity.ok().headers(headers).body(in.readAllBytes());
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				// Handle the exception as needed
			}
		}
	}

	@Operation(summary = "download_excel_report", method = "downloadExcelReport", description = "Gets the list of all Employees in excel form!!")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = ".xlsx") }),
			@ApiResponse(responseCode = "404", description = "Error while downloading.", content = {
					@Content(schema = @Schema()) }) })

	@GetMapping("/report/excel")
	public ResponseEntity<byte[]> downloadExcelReport() throws java.io.IOException {
		List<Employee> employees = registrationService.getAllEmployees();
		ByteArrayInputStream in = registrationService.createExcelReport(employees);

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=employees.xlsx");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			return ResponseEntity.ok().headers(headers).body(in.readAllBytes());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// Handle the exception as needed
			}
		}
	}

}
