package com.hacker.api.client;

import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class StudiesSheetClient {

    @Value("${google.sheets.books.spreadsheet}")
    private String spreadsheetId;

    @Value("${google.sheets.books.sheet}")
    private String bookSheetId;

    @Value("${google.sheets.books.audio.add.range}")
    private String audioBooksRange;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    public List<List<Object>> getStudies() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, bookSheetId);
        return values;
    }

    public List<Object> addBookReview(List<List<Object>> values) throws IOException {
        ValueRange body = new ValueRange().setValues(values);

        List<List<Object>> bookReview = sheetsClient
                .addValuesToSheet(spreadsheetId, audioBooksRange, body);

        return bookReview.get(0);
    }
}
