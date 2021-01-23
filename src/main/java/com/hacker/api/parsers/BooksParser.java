package com.hacker.api.parsers;

import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BooksParser extends SheetParserImpl {

    public Book parseBook(List<Object> studiesSheet) {
        Book book = (isAudioBook(studiesSheet) ? 
                    parseAudioBook(studiesSheet) : parseVisualBook(studiesSheet));
        book.setId(book.hashCode());

        return book;
    }

    public Book parseAudioBook(List<Object> studiesSheet) {
        String duration = getBookDurationInHHMM(studiesSheet);

        AudioBook book = new AudioBook();
        book.setType(BookType.AUDIO);
        book.setName(WordUtils.capitalizeFully(getAudioBookName(studiesSheet)));
        book.setAuthors(getAudioBookAuthors(studiesSheet));
        book.setDuration(parseDuration(duration));

        return book;
    }

    public Book parseVisualBook(List<Object> studiesSheet) {
        VisualBook book = new VisualBook();
        book.setType(BookType.getBookTypeByTextValue(getBookType(studiesSheet)));
        book.setName(WordUtils.capitalizeFully(getVisualBookName(studiesSheet)));
        book.setAuthors(getVisualBookAuthors(studiesSheet));
        book.setPages(getBookPageCount(studiesSheet));

        return book;
    }

    public Review parseReview(List<Object> studiesSheet) {
        Review review = null;

        if (isAudioBook(studiesSheet)) {
            review = parseAudioBookReview(studiesSheet);
        } else if (isVisualBook(studiesSheet)) {
            review = parseVisualBookReview(studiesSheet);
        }

        review.setCreated(getTimestamp(studiesSheet));
        review.setId(review.hashCode());

        return review;
    }

    public Review parseAudioBookReview(List<Object> studiesSheet) {
        Review review = new Review();
        review.setReview(getAudioBookReview(studiesSheet));
        review.setRating(getAudioBookRating(studiesSheet));

        return review;
    }

    public Review parseVisualBookReview(List<Object> studiesSheet) {
        Review review = new Review();
        review.setReview(getVisualBookReview(studiesSheet));
        review.setRating(getVisualBookRating(studiesSheet));

        return review;
    }

    private int parseDuration(String duration) {
        int hours = 0;
        int minutes = 0;

        try {
            String[] parts = duration.split(":");
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse duration"));
        } catch (NumberFormatException e) {
            logger.info(String.format("Could not parse duration"));
        }

        return hours * 60 + minutes;
    }

    public boolean isBook(List<Object> studiesSheet) {
        return isAudioBook(studiesSheet) || isVisualBook(studiesSheet);
    }

    public boolean isAudioBook(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Äänikirjabonus");
    }

    public boolean isVisualBook(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Kirjabonus");
    }

    private boolean isOfType(List<Object> studiesSheet, String type) {
        String value = getStudyType(studiesSheet).toLowerCase();

        if (!value.isEmpty() && value.equals(type.toLowerCase())) {
            return true;
        }

        return false;
    }

    private LocalDateTime getTimestamp(List<Object> studiesSheet) { return parseDateTimeValue(studiesSheet, 0); }

    public String getStudyType(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 2);
    }

    private String getAudioBookName(List<Object> studiesSheet) { return parseStringValue(studiesSheet, 5); }

    private String getBookDurationInHHMM(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 6);
    }

    private String getAudioBookAuthors(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 7);
    }

    private String getAudioBookReview(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 9);
    }

    private int getAudioBookRating(List<Object> studiesSheet) {
        return parseIntegerValue(studiesSheet, 11);
    }

    private String getVisualBookName(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 12);
    }

    private int getBookPageCount(List<Object> studiesSheet) {
        return parseIntegerValue(studiesSheet, 13);
    }

    private String getVisualBookAuthors(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 14);
    }

    private String getBookType(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 15);
    }

    private String getVisualBookReview(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 16);
    }

    private int getVisualBookRating(List<Object> studiesSheet) {
        return parseIntegerValue(studiesSheet, 18);
    }

}
