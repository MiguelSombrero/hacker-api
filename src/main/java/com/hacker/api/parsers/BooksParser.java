package com.hacker.api.parsers;

import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BooksParser extends StudiesSheetParser {

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

    public boolean isBook(List<Object> studiesSheet) {
        return isAudioBook(studiesSheet) || isVisualBook(studiesSheet);
    }

    protected boolean isAudioBook(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Äänikirjabonus");
    }

    protected boolean isVisualBook(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Kirjabonus");
    }

    private LocalDateTime getTimestamp(List<Object> studiesSheet) { return parseDateTimeValue(studiesSheet, 0); }

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
