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

    private int parseBookDuration(List<Object> row) {
        int hours = 0;
        int minutes = 0;

        try {
            String duration = getBookDurationInHHMM(row);
            String[] parts = duration.split(":");

            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse duration for row %s", row));
        } catch (NumberFormatException e) {
            logger.info(String.format("Could not parse duration for row %s", row));
        }

        return hours * 60 + minutes;
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
}
