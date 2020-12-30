package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SheetToVisualBooksParser extends SheetToBooksParserTemplate {

    private List<List<Object>> values;

    public List<Book> parseBooks() {
        return this.values.stream()
                .map(row -> parse(row))
                .collect(Collectors.toList());
    }

    protected Book parseBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setName(getBookName(row));
        book.setPages(getBookPageCount(row));
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));
        book.setAuthors(getBookAuthors(row));
        book.setId(book.hashCode());

        return book;
    }

    protected LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    protected String getEmail(List<Object> row) {
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

    protected String getBookReview(List<Object> row) {
        return parseStringValue(row, 7);
    }

    private String getBookRecommendations(List<Object> row) {
        return parseStringValue(row, 8);
    }

    protected int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 9);
    }
}
