package com.example.employee.config;

import com.example.employee.model.Department;
import com.example.employee.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentSeeder {

    @Bean
    CommandLineRunner initDepartments(DepartmentRepository departmentRepository) {
        return args -> {
            if (departmentRepository.count() == 0) {
                departmentRepository.save(new Department("IT"));
                departmentRepository.save(new Department("HR"));
                departmentRepository.save(new Department("Sales"));
                departmentRepository.save(new Department("Marketing"));
                
                System.out.println("✅ Initialized Departments: IT, HR, Sales, Marketing");
            }
        };
    }
}
