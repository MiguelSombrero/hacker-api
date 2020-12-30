package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SheetToVisualBooksParser extends SheetToBooksParserTemplate {

    public Book parseBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setPages(getBookPageCount(row));
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));

        return book;
    }

    protected LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    protected String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    protected String getBookName(List<Object> row) {
        return parseStringValue(row, 3);
    }

    private int getBookPageCount(List<Object> row) {
        return parseIntegerValue(row, 4);
    }

    protected String getBookAuthors(List<Object> row) {
        return parseStringValue(row, 5);
    }

    private String getBookType(List<Object> row) {
        return parseStringValue(row, 6);
    }

    protected String getBookReview(List<Object> row) {
        return parseStringValue(row, 7);
    }

    protected int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 9);
    }
}
