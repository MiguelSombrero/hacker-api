package com.hacker.api.parsers;

import com.hacker.api.domain.books.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SheetToAudioBooksParser extends SheetToBooksParserTemplate {

    private List<List<Object>> values;

    public List<Book> parseBooks() {
        return this.values.stream()
                .map(row -> parse(row))
                .collect(Collectors.toList());
    }

    protected Book parseBook(List<Object> row) {
        AudioBook book = new AudioBook();
        book.setName(getBookName(row));
        book.setDuration(getBookDurationInMM(row));
        book.setType(BookType.AUDIO);
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

    private String getHOPS(List<Object> row) {
        return parseStringValue(row, 3);
    }

    private String getCertificate(List<Object> row) {
        return parseStringValue(row, 4);
    }

    private String getBookName(List<Object> row) {
        return parseStringValue(row, 5);
    }

    private int getBookDurationInHHmm(List<Object> row) {
        return parseIntegerValue(row, 6);
    }

    private String getBookAuthors(List<Object> row) {
        return parseStringValue(row, 7);
    }

    private String getBookFrom(List<Object> row) {
        return parseStringValue(row, 8);
    }

    protected String getBookReview(List<Object> row) {
        return parseStringValue(row, 9);
    }

    private String getBookRecommendations(List<Object> row) {
        return parseStringValue(row, 10);
    }

    protected int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 11);
    }

    private int getBookDurationInMM(List<Object> row) {
        return parseIntegerValue(row, 16);
    }
}
