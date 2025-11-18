package com.example.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.employee.dto.DepartmentStatDTO;
import com.example.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    
    //"SELECT * FROM employees ORDER BY id DESC LIMIT 1"
    Optional<Employee> findTopByOrderByIdDesc();
    
    @Query("SELECT new com.example.employee.dto.DepartmentStatDTO(d.name, COUNT(e)) " +
            "FROM Employee e " +
            "RIGHT JOIN e.department d " +
            "GROUP BY d.id, d.name")
     List<DepartmentStatDTO> countEmployeesByDepartment();
}
