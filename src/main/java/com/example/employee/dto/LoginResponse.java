package com.example.employee.dto;

import java.util.List;

public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;

    public LoginResponse(String accessToken, String username, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }

    public String getToken() { 
    	return token; 
	}
    
    public String getType() { 
    	return type; 
	}
    
    public String getUsername() { 
    	return username; 
	}
    
    public List<String> getRoles() { 
    	return roles; 
	}
}