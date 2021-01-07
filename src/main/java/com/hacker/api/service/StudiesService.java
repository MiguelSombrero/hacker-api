package com.hacker.api.service;

import com.hacker.api.client.StudiesSheetClient;
import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import com.hacker.api.parsers.StudiesSheetParser;
import com.hacker.api.reducers.BooksReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Service
public class StudiesService {
    private Logger logger = LoggerFactory.getLogger(StudiesService.class);

    @Autowired
    private StudiesSheetClient studiesSheetClient;

    @Autowired
    private BooksReducer booksReducer;

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    public List<Book> getBooks() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Book> books = values.stream()
                .filter(this::isBook)
                .map(row -> parseBookFromRow(row))
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        List<Book> sortedBooks = books.values().stream()
                .map(book -> {
                    book.setRating(book.calculateRating());
                    return book;
                })
                .sorted()
                .collect(Collectors.toList());

        return sortedBooks;
    }

    public List<Review> getReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(this::isBook)
                .map(row -> parseReviewFromRow(row))
                .collect(Collectors.toList());

        return reviews;
    }

    private Book parseBookFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);

        Review review = studiesSheetParser.parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        Book book = (studiesSheetParser.getStudyType(row).equals("Äänikirjabonus"))
                ? studiesSheetParser.parseAudioBook(row)
                : studiesSheetParser.parseVisualBook(row);

        book.getReviews().add(review);

        return book;
    }

    private Review parseReviewFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);

        Book book = (studiesSheetParser.getStudyType(row).equals("Äänikirjabonus"))
                ? studiesSheetParser.parseAudioBook(row)
                : studiesSheetParser.parseVisualBook(row);

        Review review = studiesSheetParser.parseReview(row);
        review.setBook(book);
        review.setReviewer(reviewer);

        return review;
    }

    private boolean isBook(List<Object> row) {
        if (isAudioBook(row) || isVisualBook(row)) {
            return true;
        }

        return false;
    }

    private boolean isAudioBook(List<Object> row) {
        String value = studiesSheetParser.parseStringValue(row, 2);

        if (!value.isEmpty() && value.equals("Äänikirjabonus")) {
            return true;
        }

        return false;
    }

    private boolean isVisualBook(List<Object> row) {
        String value = studiesSheetParser.parseStringValue(row, 2);

        if (!value.isEmpty() && value.equals("Kirjabonus")) {
            return true;
        }

        return false;
    }
}
