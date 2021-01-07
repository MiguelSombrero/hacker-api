package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.books.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StudiesSheetParser extends SheetParserImpl {

    public Hacker parseStudiesHacker(List<Object> row) {
        Hacker hacker = new Hacker();
        String firstname = "";
        String lastName = "";

        try {
            String email = getEmail(row);
            String[] parts = email.split("@");
            String[] names = parts[0].split("\\.");

            firstname = WordUtils.capitalizeFully(names[0]);
            lastName = WordUtils.capitalizeFully(names[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse names from row %s", row));
        }

        hacker.setFirstname(firstname);
        hacker.setLastname(lastName);
        hacker.setId(hacker.hashCode());

        return hacker;
    }

    public Book parseAudioBook(List<Object> row) {
        AudioBook book = new AudioBook();
        book.setType(BookType.AUDIO);
        book.setName(WordUtils.capitalizeFully(getAudioBookName(row)));
        book.setAuthors(getAudioBookAuthors(row));
        book.setDuration(parseBookDuration(row));
        book.setId(book.hashCode());

        return book;
    }

    public Book parseVisualBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));
        book.setName(WordUtils.capitalizeFully(getVisualBookName(row)));
        book.setAuthors(getVisualBookAuthors(row));
        book.setPages(getBookPageCount(row));
        book.setId(book.hashCode());

        return book;
    }

    public Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));
        review.setId(review.hashCode());

        return review;
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

    private LocalDateTime getTimestamp(List<Object> row) { return parseDateTimeValue(row, 0); }

    private String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    public String getStudyType(List<Object> row) {
        return parseStringValue(row, 2);
    }

    private String getAudioBookName(List<Object> row) { return parseStringValue(row, 5); }

    private String getBookDurationInHHMM(List<Object> row) {
        return parseStringValue(row, 6);
    }

    private String getAudioBookAuthors(List<Object> row) {
        return parseStringValue(row, 7);
    }

    private String getBookReview(List<Object> row) {
        return parseStringValue(row, 9);
    }

    private int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 11);
    }

    private String getVisualBookName(List<Object> row) {
        return parseStringValue(row, 12);
    }

    private int getBookPageCount(List<Object> row) {
        return parseIntegerValue(row, 13);
    }

    private String getVisualBookAuthors(List<Object> row) {
        return parseStringValue(row, 14);
    }

    private String getBookType(List<Object> row) {
        return parseStringValue(row, 15);
    }

}
