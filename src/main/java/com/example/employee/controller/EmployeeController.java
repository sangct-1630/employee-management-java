package com.example.employee.controller;

import com.example.employee.dto.EmployeeDTO; // <-- Sửa 1
import com.example.employee.service.DepartmentService;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String listEmployees(Model model, @RequestParam(required = false) String keyword) {
        model.addAttribute("employees", employeeService.search(keyword));
        model.addAttribute("keyword", keyword);
        
        return "employees";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDTO()); 
        model.addAttribute("departments", departmentService.findAll());
        
        return "employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") EmployeeDTO employeeDto) {
        employeeService.saveOrUpdate(employeeDto); 
        
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id)); 
        model.addAttribute("departments", departmentService.findAll());
        
        return "employee-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        
        return "redirect:/";
    }
}
