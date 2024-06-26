package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.controllers;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.exception.ResourceNotFoundException;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Employee;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //build get employee REST API
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @GetMapping("/pages") //http://localhost:8080/api/v1/employees/pages?page=2&size=3
    public ResponseEntity<List<Employee>> getEmployeesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);

        Page<Employee> pageEmployees = employeeRepository.findAll(paging);
        List<Employee> employees = pageEmployees.getContent();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public List<Employee> getAllEmployeesSortedByName() {
        return employeeRepository.findAllSortedByName();
    }

    @GetMapping("/byname") //http://localhost:8080/api/v1/employees/byname?firstName=John  sort by name
    public ResponseEntity<List<Employee>> getEmployeesByName(
            @RequestParam String firstName

    ) {
        List<Employee> employees = employeeRepository.findByFirstNameOrLastName(firstName);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee with given name does not exist: " + firstName);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //build post employee REST API
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee) {

        return employeeRepository.save(employee);
    }

    //build get by id employee REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with given id does not exist!" + id));
        return ResponseEntity.ok(employee);
    }

    //build update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with given id does not exist!" + id));
        updatedEmployee.setFirstName(employeeDetails.getFirstName());
        updatedEmployee.setLastName(employeeDetails.getLastName());
        updatedEmployee.setEmployeeAge(employeeDetails.getEmployeeAge());
        updatedEmployee.setEmailId(employeeDetails.getEmailId());
        updatedEmployee.setDepartment(employeeDetails.getDepartment());

        employeeRepository.save(updatedEmployee);

        return ResponseEntity.ok(updatedEmployee);

    }
    //build Delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not exists with given id:" + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extract all validation error messages from the exception
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        // Construct the response body with all error messages
        StringBuilder responseBody = new StringBuilder("Validation(s) Violated! \n");
        for (String errorMessage : errorMessages) {
            responseBody.append(errorMessage).append("; \n");
        }

        // Remove the trailing semicolon and space
        responseBody.deleteCharAt(responseBody.length() - 1);
        responseBody.deleteCharAt(responseBody.length() - 1);

        return ResponseEntity.badRequest().body(responseBody.toString());
    }

}
