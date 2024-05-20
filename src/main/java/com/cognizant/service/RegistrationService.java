package com.cognizant.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.model.Department;
import com.cognizant.model.Employee;
import com.cognizant.repository.DepartmentRepository;
import com.cognizant.repository.EmployeeRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import jakarta.transaction.Transactional;



@Service
public class RegistrationService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Transactional
	public Employee saveOrUpdateEmployee(Employee employee) {
		// Ensure departments are saved first
		for (Department department : employee.getDepartments()) {
			if (department.getId() == null) {
				departmentRepository.save(department);
			}
		}
		return employeeRepository.save(employee);
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<Employee> getEmployeesByName(String name) {
		return employeeRepository.findByEmployeeName(name);
	}

	public List<Employee> getEmployeesByDepartment(String departmentName) {
		  Department department =
		  departmentRepository.findByDepartmentName(departmentName); return department
		  != null ? department.getEmployees() : null; }

	public List<Employee> getEmployeesByCity(String city) {
		return employeeRepository.findByAddresses_City(city);
	}
	
	public ByteArrayInputStream createPdfReport() {
        List<Employee> employees = employeeRepository.findAll(); // Fetch all employees from the database

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Generate the PDF report
        try {
            // Create a PDF document
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Add content to the document
            document.add(new Paragraph("Employee Details"));

            // Attempt to add employee details
            for (Employee employee : employees) {
                document.add(new Paragraph("Name: " + employee.getEmployeeName()));
                document.add(new Paragraph("Gender: " + employee.getGender()));
                document.add(new Paragraph("Designation: " + employee.getDesignation()));
                // Add more employee details as needed
                document.add(new Paragraph("-------------------------------------"));
            }

            // Close the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception as needed
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
	/*
	 * public ByteArrayInputStream createPdfReport(List<Employee> employees) {
	 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	 * 
	 * // Generate the PDF report try { // You can use a library like iText or
	 * Apache PDFBox to generate the PDF // For demonstration purposes, let's assume
	 * we are writing some text to the output stream
	 * outputStream.write("This is a sample PDF report".getBytes()); } catch
	 * (Exception e) { e.printStackTrace(); // Handle the exception as needed }
	 * 
	 * return new ByteArrayInputStream(outputStream.toByteArray()); }
	 */	
	public ByteArrayInputStream createExcelReport(List<Employee> employees) {
	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Employees");

	    int rowNum = 0;
	    for (Employee employee : employees) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue(employee.getEmployeeName());
	        row.createCell(1).setCellValue(employee.getGender());
	        row.createCell(2).setCellValue(employee.getDesignation());

	        // Get departments as a list of strings
	        List<Department> departments = employee.getDepartments();
	        if (departments != null && !departments.isEmpty()) {
	            // Concatenate department names into a single string
	            String departmentsString = departments.stream()
	                    .map(Department::getDepartmentName)
	                    .collect(Collectors.joining(", "));
	            row.createCell(3).setCellValue(departmentsString);
	        } else {
	            row.createCell(3).setCellValue(""); // Set empty string if departments are null or empty
	        }
	        // Add more columns as needed
	    }

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    try {
	        workbook.write(outputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return new ByteArrayInputStream(outputStream.toByteArray());
	}

}
