package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Department;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.Employee;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository.DepartmentRepository;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringbootEmployeeDepartmentErnestGeorkianiBackendProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEmployeeDepartmentErnestGeorkianiBackendProjectApplication.class, args);
	}

	@Autowired

	private EmployeeRepository employeeRepository;
	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public void run(String... args) throws Exception {

		// Create employees
		Employee employee1 = new Employee();
		employee1.setFirstName("John");
		employee1.setLastName("Doe");
		employee1.setEmployeeAge(18);
		employee1.setEmailId("johndoe@gmail.com");
		employeeRepository.save(employee1);

		Employee employee2 = new Employee();
		employee2.setFirstName("Steve");
		employee2.setLastName("Tompson");
		employee2.setEmployeeAge(24);
		employee2.setEmailId("tommys@gmail.com");
		employeeRepository.save(employee2);

		Employee employee3 = new Employee();
		employee3.setFirstName("Robert");
		employee3.setLastName("Langdon");
		employee3.setEmployeeAge(45);
		employee3.setEmailId("robert2024@gmail.com");
		employeeRepository.save(employee3);

		List<Employee> employees = employeeRepository.findAll();

		// Create departments
		Department department1 = new Department();
		department1.setDepName("IT Department");
		department1.setDepLocation("New Jersey");
		department1.setEmployee(employees);
		departmentRepository.save(department1);

		Department department2 = new Department();
		department2.setDepName("Marketing Department");
		department2.setDepLocation("Arlington");
		department2.setEmployee(employees);
		departmentRepository.save(department2);

		Department department3 = new Department();
		department3.setDepName("HR Department");
		department3.setDepLocation("Dallas");
		department3.setEmployee(employees);
		departmentRepository.save(department3);
	}

}
