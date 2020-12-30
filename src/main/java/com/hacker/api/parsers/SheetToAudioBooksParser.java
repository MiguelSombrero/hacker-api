package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SheetToAudioBooksParser extends SheetToBooksParserTemplate {

    public Book parseBook(List<Object> row) {
        AudioBook book = new AudioBook();
        book.setDuration(getBookDurationInMM(row));
        book.setType(BookType.AUDIO);

        return book;
    }

    protected LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    protected String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    protected String getBookName(List<Object> row) {
        return parseStringValue(row, 5);
    }

    protected String getBookAuthors(List<Object> row) {
        return parseStringValue(row, 7);
    }

    protected String getBookReview(List<Object> row) {
        return parseStringValue(row, 9);
    }

    protected int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 11);
    }

    private int getBookDurationInMM(List<Object> row) {
        return parseIntegerValue(row, 16);
    }
}
