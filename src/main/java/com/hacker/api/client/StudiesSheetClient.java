package com.hacker.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class StudiesSheetClient {

    @Value("${google.sheets.books.spreadsheet}")
    private String spreadsheetId;

    @Value("${google.sheets.books.sheet}")
    private String bookSheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    public List<List<Object>> getStudies() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, bookSheetId);
        return values;
    }
}
