package com.example.SpringMVC.dao;


import com.example.SpringMVC.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}


