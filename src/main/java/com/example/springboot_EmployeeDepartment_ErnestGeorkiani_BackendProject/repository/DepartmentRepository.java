package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Department;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d ORDER BY d.depName")
    List<Department> findAllSortedByName();

    @Query("SELECT d FROM Department d WHERE d.depName = :name OR d.depLocation = :name")
    List<Department> findByDepNameOrDepLocation(String name);

}
