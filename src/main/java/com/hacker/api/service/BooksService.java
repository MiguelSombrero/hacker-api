package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.parsers.SheetToVisualBooksParser;
import com.hacker.api.reducers.BooksReducer;
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
    private String bookSheetId;

    @Value("${google.sheets.books.audio.sheet}")
    private String audioSheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private BooksReducer reducer;

    public Collection<Book> getBooks() throws IOException {
        /*List<String> ranges = Arrays.asList(bookSheetId, audioSheetId);

        List<ValueRange> values = sheetsClient.getValuesFromSheets(spreadsheetId, ranges);
*/

        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, bookSheetId);

        values.remove(0);

        SheetToVisualBooksParser parser = new SheetToVisualBooksParser(values);

        Collection<Book> books = reducer.reduce(parser.parseBooks());

        books.stream().forEach(Book::calculateRating);

        return books;
    }

    /*public Collection<Book> getBooks() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, sheetId);

        SpreadsheetToBooksParser parser = new SpreadsheetToBooksParser(values);

        Collection<Book> books = reducer.reduce(parser.parseBooks());

        books.stream().forEach(Book::calculateRating);

        return books;
    }*/
}
