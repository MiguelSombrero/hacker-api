package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.books.*;
import org.apache.commons.text.WordUtils;

import java.time.LocalDateTime;
import java.util.List;

public abstract class SheetToBooksParserTemplate extends SheetParserImpl {

    public Book parse(List<Object> row) {
        Hacker reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        Book book = parseBook(row);

        book.setName(WordUtils.capitalizeFully(getBookName(row)));
        book.setAuthors(getBookAuthors(row));
        book.setId(book.hashCode());
        book.getReviews().add(review);

        return book;
    }

    private Hacker parseEmployee(List<Object> row) {
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

    private Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));

        return review;
    }

    private LocalDateTime getTimestamp(List<Object> row) {
        return parseDateTimeValue(row, 0);
    }

    private String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    protected abstract String getBookReview(List<Object> row);
    protected abstract int getBookRating(List<Object> row);
    protected abstract Book parseBook(List<Object> row);
    protected abstract String getBookName(List<Object> row);
    protected abstract String getBookAuthors(List<Object> row);
}
