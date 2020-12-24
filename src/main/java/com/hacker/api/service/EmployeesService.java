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
    private GoogleSheetsToEmployeesTransformer transformer;

    public Collection<Employee> getEmployees() throws IOException {
        List<List<Object>> response = sheetsClient.getValuesFromSheet(spredsheetId, sheetId);
        Collection<Employee> employees = transformer.transform(response);
        return employees;
    }

}
