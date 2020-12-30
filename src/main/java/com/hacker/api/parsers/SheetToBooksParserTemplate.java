package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.*;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public abstract class SheetToBooksParserTemplate extends SheetParserTemplate {
    protected static Logger logger = LoggerFactory.getLogger(SheetToBooksParserTemplate.class);

    protected Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        String firstname = "";
        String lastName = "";

        try {
            String email = getEmail(row);
            String[] parts = email.split("@");
            String[] names = parts[0].split("\\.");

            firstname = names[0];
            lastName = names[1];

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse names from row %s", row));
        }

        employee.setFirstname(firstname);
        employee.setLastname(lastName);
        employee.setId(employee.hashCode());

        return employee;
    }

    protected Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));

        return review;
    }

    protected Book parse(List<Object> row) {
        logger.info(String.format("Parsing row %s", row));

        Employee reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        Book book = parseBook(row);
        book.getReviews().add(review);

        return book;
    }

    protected abstract String getEmail(List<Object> row);
    protected abstract LocalDateTime getTimestamp(List<Object> row);
    protected abstract String getBookReview(List<Object> row);
    protected abstract int getBookRating(List<Object> row);
    protected abstract Book parseBook(List<Object> row);
}
