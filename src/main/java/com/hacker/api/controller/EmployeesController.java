package com.hacker.api.controller;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Book;
import com.hacker.api.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    @GetMapping(path = "/", produces = "application/json")
    @ResponseBody
    public Collection<Employee> getEmployees() throws IOException {
        Collection<Employee> result = service.getEmployees();
        return result;
    }
}
