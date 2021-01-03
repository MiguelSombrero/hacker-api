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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

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

        Map<Integer, Book> books = Stream.concat(visualBooks.stream(), audioBooks.stream())
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        books.values().stream().forEach(book -> book.setRating(book.calculateRating()));

        return new ArrayList<>(books.values());
    }

    private List<Book> parseBooks(List<List<Object>> values, SheetParserImpl parser) {
        List<Book> books = values.stream()
                .map(row -> (Book) parser.parse(row))
                .collect(Collectors.toList());

        return books;
    }

}
