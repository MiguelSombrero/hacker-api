package com.hacker.api.service;

import com.google.api.services.sheets.v4.model.ValueRange;
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
import java.util.Arrays;
import java.util.Collection;
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
    private BooksReducer reducer;

    @Autowired
    private SheetToAudioBooksParser audioBooksParser;

    @Autowired
    private SheetToVisualBooksParser visualBooksParser;

    public Collection<Book> getBooks() throws IOException {
        List<String> ranges = Arrays.asList(bookSheetId, audioSheetId);

        List<ValueRange> values = sheetsClient.getValuesFromMultipleSheet(spreadsheetId, ranges);

        List<List<Object>> visualBookValues = values.get(0).getValues();
        List<List<Object>> audioBookValues = values.get(1).getValues();

        visualBookValues.remove(0);
        audioBookValues.remove(0);

        Collection<Book> visualBooks = visualBookValues.stream()
                .map(row -> visualBooksParser.parse(row))
                .collect(Collectors.toList());

        Collection<Book> audioBooks = audioBookValues.stream()
                .map(row -> audioBooksParser.parse(row))
                .collect(Collectors.toList());

        Collection<Book> books = Stream.concat(visualBooks.stream(), audioBooks.stream())
                .collect(Collectors.toList());

        books = reducer.reduce(books);

        books.stream().forEach(book -> book.calculateRating(book.getReviews()));

        return books;
    }

}
