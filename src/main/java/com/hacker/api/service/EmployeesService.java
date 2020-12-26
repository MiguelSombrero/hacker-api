package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Employee;
import com.hacker.api.reducers.EmployeesReducer;
import com.hacker.api.parsers.SpreadsheetToEmployeesParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

    public Collection<Employee> getEmployees() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spredsheetId, sheetId);

        SpreadsheetToEmployeesParser parser = new SpreadsheetToEmployeesParser(values);

        Collection<Employee> employees = reducer.reduce(parser.parseEmployees());

        return employees;
    }

}
