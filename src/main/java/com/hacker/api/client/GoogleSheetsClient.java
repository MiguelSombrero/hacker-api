package com.hacker.api.client;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GoogleSheetsClient {
    protected Logger logger = LoggerFactory.getLogger(GoogleSheetsClient.class);

    @Autowired
    private Sheets sheetsClient;

    public List<List<Object>> getValuesFromSheet(String spreadsheetId, String sheetId) throws IOException {
        List<List<Object>> response = sheetsClient.spreadsheets()
                .values()
                .get(spreadsheetId, sheetId)
                .execute()
                .getValues();

        return response;
    }

    public List<ValueRange> getValuesFromMultipleSheet(String spreadsheetId, List<String> ranges) throws IOException {
        List<ValueRange> response = sheetsClient.spreadsheets()
                .values()
                .batchGet(spreadsheetId)
                .setRanges(ranges)
                .execute().getValueRanges();

        return response;
    }
}
