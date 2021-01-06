package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SheetToVisualBooksParser extends SheetToBooksParserTemplate {

    protected Book parseBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setPages(getBookPageCount(row));
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));

        return book;
    }

    protected String getBookName(List<Object> row) {
        return parseStringValue(row, 12);
    }

    private int getBookPageCount(List<Object> row) {
        return parseIntegerValue(row, 13);
    }

    protected String getBookAuthors(List<Object> row) {
        return parseStringValue(row, 14);
    }

    private String getBookType(List<Object> row) {
        return parseStringValue(row, 15);
    }

    protected String getBookReview(List<Object> row) {
        return parseStringValue(row, 16);
    }

    protected int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 18);
    }
}
