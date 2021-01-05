package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SheetToAudioBooksParser extends SheetToBooksParserTemplate {

    protected Book parseBook(List<Object> row) {
        AudioBook book = new AudioBook();
        book.setDuration(parseBookDuration(row));
        book.setType(BookType.AUDIO);

        return book;
    }

    protected LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    protected String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    protected String getBookName(List<Object> row) { return parseStringValue(row, 5); }

    private String getBookDurationInHHMM(List<Object> row) {
        return parseStringValue(row, 6);
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

    private int parseBookDuration(List<Object> row) {
        String[] parts = getBookDurationInHHMM(row).split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return hours * 60 + minutes;
    }
}
