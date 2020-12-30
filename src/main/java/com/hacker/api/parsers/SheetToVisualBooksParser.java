package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SheetToVisualBooksParser extends SpreadsheetParserTemplate {

    private List<List<Object>> values;

    public List<Book> parseBooks() {
        return this.values.stream()
                .map(row -> mapToBook(row))
                .collect(Collectors.toList());
    }

    private VisualBook mapToBook(List<Object> row) {
        logger.info(String.format("Parsing row %s", row));

        Employee reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        VisualBook book = parseBook(row);
        book.setAuthors(getBookAuthors(row));
        book.setId(book.hashCode());
        book.getReviews().add(review);

        return book;
    }

    private VisualBook parseBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setName(getBookName(row));
        book.setPages(getBookPageCount(row));
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));

        return book;
    }

    private Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));

        return review;
    }

    private Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        String firstname = "";
        String lastName = "";

        try {
            String email = getEmail(row);
            String[] parts = email.split("@");
            String[] names = parts[0].split("\\.");

            firstname = names[0];
            lastName = names[1];

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse names from row %s", row));
        }

        employee.setFirstname(firstname);
        employee.setLastname(lastName);
        employee.setId(employee.hashCode());

        return employee;
    }

    private LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    private String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    private String getBonusType(List<Object> row) {
        return parseStringValue(row, 2);
    }

    private String getBookName(List<Object> row) {
        return parseStringValue(row, 3);
    }

    private int getBookPageCount(List<Object> row) {
        return parseIntegerValue(row, 4);
    }

    private String getBookAuthors(List<Object> row) {
        return parseStringValue(row, 5);
    }

    private String getBookType(List<Object> row) {
        return parseStringValue(row, 6);
    }

    private String getBookReview(List<Object> row) {
        return parseStringValue(row, 7);
    }

    private String getBookRecommendations(List<Object> row) {
        return parseStringValue(row, 8);
    }

    private int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 9);
    }
}
