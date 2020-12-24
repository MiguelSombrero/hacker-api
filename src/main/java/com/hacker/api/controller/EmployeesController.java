package com.hacker.api.controller;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Book;
import com.hacker.api.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.rmi.RemoteException;
import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService service;

    @GetMapping("")
    public Collection<Employee> getEmployees() throws IOException {
        Collection<Employee> result = service.getEmployees();
        return result;
    }
}
