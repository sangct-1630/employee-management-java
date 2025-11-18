package com.example.employee.dto;

public class DepartmentStatDTO {
    private String departmentName;
    private Long count;

    public DepartmentStatDTO(String departmentName, Long count) {
        this.departmentName = departmentName;
        this.count = count;
    }

    // Getters and Setters
    public String getDepartmentName() { 
    	return departmentName; 
	}
    
    public void setDepartmentName(String departmentName) { 
    	this.departmentName = departmentName; 
	}
    
    public Long getCount() { 
    	return count; 
	}
    
    public void setCount(Long count) { 
    	this.count = count; 
	}
}
