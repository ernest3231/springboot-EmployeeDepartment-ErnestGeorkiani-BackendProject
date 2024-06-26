package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "department_name")
    @NotEmpty(message = "Department Name should be Specified!")
    private String depName;

    @Column(name = "department_location")
    @NotEmpty(message = "Department Location should be Specified!")
    private String depLocation;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Employee> employee;

}
