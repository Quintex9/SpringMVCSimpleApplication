package com.example.SpringMVC.dao;


import com.example.SpringMVC.entity.Employee;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    boolean existsByEmail(@Pattern(
            regexp = "^[A-Za-z0-9%.]{1,64}@[A-Za-z0-9.]{2,190}\\.[A-Za-z]{2,20}$",
            message = "Email musí byť vo správnom formáte"
    ) String email);

    Employee findByEmail(@Pattern(
            regexp = "^[A-Za-z0-9%.]{1,64}@[A-Za-z0-9.]{2,190}\\.[A-Za-z]{2,20}$",
            message = "Email musí byť vo správnom formáte"
    ) String email);
}


