package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EmployeeService(
		EmployeeRepository employeeRepository, 
		DepartmentRepository departmentRepository, 
		ModelMapper modelMapper
	) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    private EmployeeDTO convertToDto(Employee employee) {
        EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
        if (employee.getDepartment() != null) {
            dto.setDepartmentName(employee.getDepartment().getName());
        }
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = modelMapper.map(dto, Employee.class);
        if (dto.getDepartmentId() != null) {
            employee.setDepartment(departmentRepository.findById(dto.getDepartmentId())
                    .orElse(null));
        }
        return employee;
    }
    
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return convertToDto(employee);
    }

    public EmployeeDTO save(EmployeeDTO dto) {
        Employee employee = convertToEntity(dto);
        Employee savedEmployee = employeeRepository.save(employee);
        
        return convertToDto(savedEmployee);
    }

    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        System.out.println("update");

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        existingEmployee.setFirstName(dto.getFirstName());
        existingEmployee.setLastName(dto.getLastName());
        existingEmployee.setEmail(dto.getEmail());
        existingEmployee.setPosition(dto.getPosition());
        
        if (dto.getDepartmentId() != null) {
            existingEmployee.setDepartment(departmentRepository.findById(dto.getDepartmentId())
                    .orElse(null));
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        
        return convertToDto(updatedEmployee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
    
    public List<EmployeeDTO> search(String keyword) {
        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public EmployeeDTO saveOrUpdate(EmployeeDTO dto) {
        Employee employee;

        if (dto.getId() == null) {
            employee = modelMapper.map(dto, Employee.class);
            employee.setEmployeeCode(generateNewEmployeeCode());
            
        } else {
            employee = employeeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found with id: " + dto.getId()));

            employee.setFirstName(dto.getFirstName());
            employee.setLastName(dto.getLastName());
            employee.setEmail(dto.getEmail());
            employee.setPosition(dto.getPosition());
        }

        if (dto.getDepartmentId() != null) {
            Department dept = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(dept);
        } else {
            employee.setDepartment(null);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDto(savedEmployee);
    }
    
    private String generateNewEmployeeCode() {
        long lastId = employeeRepository.findTopByOrderByIdDesc()
			.map(emp -> emp.getId())
            .orElse(0L);
        
        long newId = lastId + 1;
        
        return String.format("NV%03d", newId);
    }
    
    @Cacheable("employeeCount") 
    public long countEmployees() {
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        logger.info("-----> Đang truy vấn Database để đếm nhân viên...");
        return employeeRepository.count();
    }
}
