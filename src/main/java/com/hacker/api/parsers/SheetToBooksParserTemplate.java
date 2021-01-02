package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.*;
import org.apache.commons.text.WordUtils;

import java.time.LocalDateTime;
import java.util.List;

public abstract class SheetToBooksParserTemplate extends SheetParserImpl {

    public Object parse(List<Object> row) {
        Employee reviewer = parseEmployee(row);

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

    private Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
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

        employee.setFirstname(firstname);
        employee.setLastname(lastName);
        employee.setId(employee.hashCode());

        return employee;
    }

    private Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));

        return review;
    }

    protected abstract String getEmail(List<Object> row);
    protected abstract LocalDateTime getTimestamp(List<Object> row);
    protected abstract String getBookReview(List<Object> row);
    protected abstract int getBookRating(List<Object> row);
    protected abstract Book parseBook(List<Object> row);
    protected abstract String getBookName(List<Object> row);
    protected abstract String getBookAuthors(List<Object> row);
}
