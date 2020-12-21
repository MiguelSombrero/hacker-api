package com.hacker.api.service;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Employee;
import com.hacker.api.utils.GoogleSheetsToEmployeesTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

@Service
public class EmployeesService {
    private Logger logger = LoggerFactory.getLogger(EmployeesService.class);

    @Value("${google.sheets.projects.id}")
    private String id;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    public Collection<Employee> getEmployees() throws IOException {
        ValueRange response = sheetsClient.getValuesFromSheet(id);

        Collection<Employee> employees = GoogleSheetsToEmployeesTransformer
                .transform(response.getValues());

        return employees;
    }

}
