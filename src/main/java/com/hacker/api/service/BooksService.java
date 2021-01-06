package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.parsers.SheetToAudioBooksParser;
import com.hacker.api.parsers.SheetToVisualBooksParser;
import com.hacker.api.reducers.BooksReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Service
public class BooksService {
    private Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Value("${google.sheets.books.spreadsheet}")
    private String spreadsheetId;

    @Value("${google.sheets.books.sheet}")
    private String bookSheetId;

    @Autowired
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private BooksReducer booksReducer;

    @Autowired
    private SheetToAudioBooksParser audioBooksParser;

    @Autowired
    private SheetToVisualBooksParser visualBooksParser;

    public List<Book> getBooks() throws IOException {
        List<List<Object>> values = sheetsClient.getValuesFromSheet(spreadsheetId, bookSheetId);

        Map<Integer, Book> books = values.stream()
                .filter(this::isBook)
                .map(row -> (isVisualBook(row)) ? visualBooksParser.parse(row) : audioBooksParser.parse(row))
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        books.values().stream().forEach(book -> book.setRating(book.calculateRating()));

        return new ArrayList<>(books.values());
    }

    private boolean isBook(List<Object> row) {
        if (isAudioBook(row) || isVisualBook(row)) {
            return true;
        }

        return false;
    }

    private boolean isAudioBook(List<Object> row) {
        String value = audioBooksParser.parseStringValue(row, 2);

        if (!value.isEmpty() && value.equals("Äänikirjabonus")) {
            return true;
        }

        return false;
    }

    private boolean isVisualBook(List<Object> row) {
        String value = visualBooksParser.parseStringValue(row, 2);

        if (!value.isEmpty() && value.equals("Kirjabonus")) {
            return true;
        }

        return false;
    }
}
