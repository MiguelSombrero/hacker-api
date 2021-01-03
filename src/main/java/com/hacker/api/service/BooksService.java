package com.hacker.api.service;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.books.Book;
import com.hacker.api.parsers.SheetParserImpl;
import com.hacker.api.parsers.SheetToAudioBooksParser;
import com.hacker.api.parsers.SheetToVisualBooksParser;
import com.hacker.api.reducers.BooksReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private BooksReducer booksReducer;

    @Autowired
    private SheetToAudioBooksParser audioBooksParser;

    @Autowired
    private SheetToVisualBooksParser visualBooksParser;

    public List<Book> getBooks() throws IOException {
        List<String> ranges = Arrays.asList(bookSheetId, audioSheetId);
        List<ValueRange> values = sheetsClient.getValuesFromMultipleSheet(spreadsheetId, ranges);

        List<Book> visualBooks = parseBooks(values.get(0).getValues(), visualBooksParser);
        List<Book> audioBooks = parseBooks(values.get(1).getValues(), audioBooksParser);

        List<Book> books = Stream.concat(visualBooks.stream(), audioBooks.stream())
                .collect(Collectors.toList());

        books = booksReducer.reduce(books);

        books.stream().forEach(book -> book.setRating(book.calculateRating()));

        return books;
    }

    private List<Book> parseBooks(List<List<Object>> values, SheetParserImpl parser) {
        values.remove(0);

        List<Book> books = values.stream()
                .map(row -> (Book) parser.parse(row))
                .collect(Collectors.toList());

        return books;
    }

}
