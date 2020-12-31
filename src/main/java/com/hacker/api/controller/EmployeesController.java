package com.hacker.api.controller;

import com.hacker.api.domain.Employee;
import com.hacker.api.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    @GetMapping("")
    public List<Employee> getEmployees() throws IOException {
        List<Employee> result = service.getEmployees();
        return result;
    }
}
