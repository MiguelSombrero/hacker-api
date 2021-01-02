package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Hacker;
import com.hacker.api.reducers.HackersReducer;
import com.hacker.api.parsers.SheetToHackerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HackersService {
    private Logger logger = LoggerFactory.getLogger(HackersService.class);

    @Value("${google.sheets.projects.spreadsheet}")
    private String spredsheetId;

    @Value("${google.sheets.projects.sheet}")
    private String sheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private HackersReducer reducer;

    @Autowired
    private SheetToHackerParser sheetToHackerParser;

    public List<Hacker> getEmployees() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spredsheetId, sheetId);

        List<Hacker> hackers = values.stream()
                .map(row -> (Hacker) sheetToHackerParser.parse(row))
                .collect(Collectors.toList());

        hackers = reducer.reduce(hackers);

        return hackers;
    }

}
