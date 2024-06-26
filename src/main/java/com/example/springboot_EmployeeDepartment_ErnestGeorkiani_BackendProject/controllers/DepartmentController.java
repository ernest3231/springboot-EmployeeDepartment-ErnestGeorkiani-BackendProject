package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.controllers;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.exception.ResourceNotFoundException;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Department;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Employee;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v2/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/all")
    public List<Department> getAllDepartments() {

        return departmentRepository.findAll();
    }

    @GetMapping("/pages") //http://localhost:8080/api/v2/departments/pages?page=1&size=2
    public ResponseEntity<List<Department>> getDepartmentWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);

        Page<Department> pageDepartments = departmentRepository.findAll(paging);
        List<Department> departments = pageDepartments.getContent();

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public List<Department> getAllDepartmentsSortedByName() {

        return departmentRepository.findAllSortedByName();
    }

    @GetMapping("/bydepname") //http://localhost:8080/api/v2/departments/bydepname?name=IT Department
    public ResponseEntity<List<Department>> getDepartmentsByName(
            @RequestParam String name

    ) {
        List<Department> departments = departmentRepository.findByDepNameOrDepLocation(name);

        if (departments.isEmpty()) {
            throw new ResourceNotFoundException("Employee with given name does not exist: " + name);
        }

        return new ResponseEntity<>(departments, HttpStatus.OK);
    }


    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PostMapping("/create")
    public Department createObject(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @GetMapping("{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Such department does not exist!"));
        return ResponseEntity.ok(department);
    }

    @PutMapping("{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestBody Department departmentDetails) {

        Department updatedDepartment = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Such department does not exist!"));

        updatedDepartment.setDepName(departmentDetails.getDepName());
        updatedDepartment.setDepLocation(departmentDetails.getDepLocation());
        updatedDepartment.setEmployee(departmentDetails.getEmployee());

        departmentRepository.save(updatedDepartment);

        return ResponseEntity.ok(updatedDepartment);


    }
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteDepartment(@PathVariable long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Such department does not exist"));

        departmentRepository.delete(department);

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


//{
//        "depName": "Beta Department",
//        "depLocation": "San Francisco",
//        "employee": [
//        {"id": 166},
//        {"id": 167},
//        {"id": 168}
//        ]
//        }