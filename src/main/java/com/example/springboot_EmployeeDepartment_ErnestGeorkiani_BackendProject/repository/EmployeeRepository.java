package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e ORDER BY CONCAT(e.firstName, ' ', e.lastName)")
    List<Employee> findAllSortedByName();

    @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 OR e.lastName = ?1")
    List<Employee> findByFirstNameOrLastName(String name);

}
