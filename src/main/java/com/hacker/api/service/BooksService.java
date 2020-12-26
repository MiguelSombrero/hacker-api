package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.reducers.BooksReducer;
import com.hacker.api.parsers.SpreadsheetToBooksParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
    private BooksReducer reducer;

    public Collection<Book> getBooks() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, sheetId);

        SpreadsheetToBooksParser parser = new SpreadsheetToBooksParser(values);

        Collection<Book> books = reducer.reduce(parser.parseBooks());

        books.stream().forEach(book -> book.calculateRating());

        return books;
    }

}
