package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.utils.GoogleSheetsToBooksTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Collection;

@Service
public class BooksService {
    private Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Value("${google.sheets.books.spreadsheet}")
    private String spreadsheetId;

    @Value("${google.sheets.books.sheet}")
    private String sheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private GoogleSheetsToBooksTransformer transformer;

    public Collection<Book> getBooks() throws IOException {
        ValueRange response = sheetsClient.getValuesFromSheet(spreadsheetId, sheetId);
        Collection<Book> books = transformer.transform(response.getValues());
        return books;
    }

}
