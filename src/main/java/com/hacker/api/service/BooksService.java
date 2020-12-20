package com.hacker.api.service;

import com.google.api.services.sheets.v4.Sheets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;

@Service
public class BooksService {
    private Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Value("${google.sheets.books.id}")
    private String id;

    @Value("${google.sheets.books.range}")
    private String range;

    @Autowired
    private Sheets sheetsClient;

    public String getBooks() throws IOException {
        ValueRange response = sheetsClient.spreadsheets().values()
                .get(id, range)
                .execute();

        return response.toPrettyString();
    }



}
