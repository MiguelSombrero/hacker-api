package com.hacker.api.client;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoogleSheetsClient {

    @Autowired
    private Sheets sheetsClient;

    public ValueRange getValuesFromSheet(String spreadsheetId, String sheetId) throws IOException {
        ValueRange response = sheetsClient.spreadsheets()
                .values()
                .get(spreadsheetId, sheetId)
                .execute();

        return response;
    }
}
