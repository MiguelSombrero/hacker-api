package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Employee;
import com.hacker.api.reducers.EmployeesReducer;
import com.hacker.api.parsers.SheetToEmployeeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeesService {
    private Logger logger = LoggerFactory.getLogger(EmployeesService.class);

    @Value("${google.sheets.projects.spreadsheet}")
    private String spredsheetId;

    @Value("${google.sheets.projects.sheet}")
    private String sheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private EmployeesReducer reducer;

    @Autowired
    private SheetToEmployeeParser sheetToEmployeeParser;

    public List<Employee> getEmployees() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spredsheetId, sheetId);

        List<Employee> employees = values.stream()
                .map(row -> (Employee) sheetToEmployeeParser.parse(row))
                .collect(Collectors.toList());

        employees = reducer.reduce(employees);

        return employees;
    }

}
