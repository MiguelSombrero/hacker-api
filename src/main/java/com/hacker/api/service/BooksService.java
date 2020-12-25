package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.reducers.BookReducer;
import com.hacker.api.parsers.BooksParser;
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
    private BookReducer reducer;

    public Collection<Book> getBooks() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, sheetId);

        BooksParser parser = new BooksParser(values);

        Collection<Book> books = reducer.reduce(parser.getAll());

        books.stream().forEach(book -> book.calculateRating());

        return books;
    }

}
