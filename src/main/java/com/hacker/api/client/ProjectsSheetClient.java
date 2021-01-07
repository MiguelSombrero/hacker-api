package com.hacker.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProjectsSheetClient {

    @Value("${google.sheets.projects.spreadsheet}")
    private String spreadsheetId;

    @Value("${google.sheets.projects.sheet}")
    private String sheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    public List<List<Object>> getProjects() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, sheetId);
        return values;
    }
}
