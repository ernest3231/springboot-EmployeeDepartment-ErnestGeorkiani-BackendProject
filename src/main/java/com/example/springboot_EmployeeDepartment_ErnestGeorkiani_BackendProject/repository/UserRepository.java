package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}