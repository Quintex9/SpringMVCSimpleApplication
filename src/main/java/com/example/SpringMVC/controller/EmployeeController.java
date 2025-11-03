package com.example.SpringMVC.controller;

import com.example.SpringMVC.entity.Employee;
import com.example.SpringMVC.exception.EmailAlreadyExistsException;
import com.example.SpringMVC.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("jobTitles", jobTitles);
            model.addAttribute("jobTypes", jobTypes);
            return "employee/form";
        }

        try {
            employeeService.save(employee);
            return "redirect:/employees";
        } catch (EmailAlreadyExistsException ex) {
            bindingResult.rejectValue("email", "email.exists", ex.getMessage());
            model.addAttribute("jobTitles", jobTitles);
            model.addAttribute("jobTypes", jobTypes);
            return "employee/form";
        }
    }


    @Value("${jobTitles}")
    private List<String> jobTitles;

    @Value("${jobTypes}")
    private List<String> jobTypes;

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employee/list";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employee/view";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("jobTitles", jobTitles);
        model.addAttribute("jobTypes", jobTypes);
        return "employee/form";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("jobTitles", jobTitles);
        model.addAttribute("jobTypes", jobTypes);
        return "employee/form";
    }

    @PostMapping("/new")
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }
}
