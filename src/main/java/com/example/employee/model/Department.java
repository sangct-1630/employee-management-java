package com.example.employee.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();
    
    public Department() {
    }

    // Using seeder
    public Department(String name) {
        this.name = name;
    }
    
    // Getters and Setters
    public Long getId() { 
    	return id; 
	}
    
    public void setId(Long id) { 
    	this.id = id; 
	}
    
    public String getName() { 
    	return name; 
	}
    
    public void setName(String name) { 
    	this.name = name; 
	}
    
    public List<Employee> getEmployees() { 
    	return employees; 
	}
    
    public void setEmployees(List<Employee> employees) { 
    	this.employees = employees; 
	}
}
