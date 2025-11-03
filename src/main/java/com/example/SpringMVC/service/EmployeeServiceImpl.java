package com.example.SpringMVC.service;

import com.example.SpringMVC.dao.EmployeeRepository;
import com.example.SpringMVC.entity.Employee;
import com.example.SpringMVC.exception.EmailAlreadyExistsException;
import com.example.SpringMVC.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Employee",id));
    }


    @Transactional
    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == 0) {
            if(employeeRepository.existsByEmail(employee.getEmail())) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        } else{
            Employee existingWithEmail = employeeRepository.findByEmail(employee.getEmail());
            if (existingWithEmail != null && existingWithEmail.getId() != employee.getId()) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        employeeRepository.deleteById(id);
    }
}

