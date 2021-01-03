package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Hacker;
import com.hacker.api.reducers.HackersReducer;
import com.hacker.api.parsers.SheetToHackerParser;
import com.hacker.api.reducers.SkillsReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.reducing;

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
    private HackersReducer hackersReducer;

    @Autowired
    private SkillsReducer skillsReducer;

    @Autowired
    private SheetToHackerParser sheetToHackerParser;

    public List<Hacker> getHackers() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spredsheetId, sheetId);
        List<Hacker> hackers = parseHackers(values);

        return hackers;
    }

    private List<Hacker> parseHackers(List<List<Object>> values) {
        Map<Integer, Hacker> hackers = values.stream()
                .map(row -> (Hacker) sheetToHackerParser.parse(row))
                .collect(groupingBy(Hacker::getId, reducing(null, hackersReducer.reduce())));

        return new ArrayList<>(hackers.values());
    }

}
