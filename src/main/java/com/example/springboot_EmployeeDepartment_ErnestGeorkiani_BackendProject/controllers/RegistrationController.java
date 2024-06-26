package com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.controllers;

import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.models.User;
import com.example.springboot_EmployeeDepartment_ErnestGeorkiani_BackendProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;


@Controller
@RequestMapping("/api/users")  // Base mapping for all endpoints in this controller
public class RegistrationController {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, String> requestBody, Model model) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        try {
            // Validate input (e.g., check if username already exists)
            User existingUser = userRepository.findByUsername(username);
            if (existingUser != null) {
                // Handle duplicate username error
                model.addAttribute("error", "Username already exists. Please choose another username.");
                return "registration"; // Return to registration form with error message
            }

            // Create a new user object with hashed password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            User newUser = new User(username, hashedPassword);
            // You can set other fields of the user object here if needed

            // Save the user object
            userRepository.save(newUser);

            // Redirect to login page or other success page
            return "registration"; // Update with your actual login page URL
        } catch (Exception e) {
            // Handle any exceptions that occur during registration
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "registration"; // Return to registration form with error message
        }
    }
}