package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Name cannot be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Name cannot be empty")
    private String lastName;

    @Column(name = "employee_age")
    @Min(value = 18, message = "Age must be greater than or equal to 18")
    @Max(value = 55, message = "Age must be less than or equal to 55")
    private Integer employeeAge;

    @Column(name = "email_id")
    @Email(message = "Invalid email format")
    private String emailId;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
