package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Author;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SpreadsheetToBooksParser extends SpreadsheetParserTemplate {

    private List<List<Object>> values;

    public List<Book> parseBooks() {
        return this.values.stream()
                .map(row -> mapToBook(row))
                .collect(Collectors.toList());
    }

    private Book mapToBook(List<Object> row) {
        List<Author> authors = parseAuthors(row);
        Employee reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        Book book = parseBook(row);
        book.setAuthors(authors);
        book.setId(book.hashCode());
        book.getReviews().add(review);

        return book;
    }

    private List<Author> parseAuthors(List<Object> row) {
        String text = getStringValue(row, 0);
        String[] parts = text.split(";");

        List<Author> authors = Arrays.stream(parts)
                .map(part -> {
                    Author author = new Author();
                    author.setFirstname(part.split(",")[1].trim());
                    author.setLastname(part.split(",")[0].trim());
                    author.setId(author.hashCode());

                    return author;
                })
                .collect(Collectors.toList());

        return authors;
    }

    private Book parseBook(List<Object> row) {
        Book book = new Book();
        book.setName(getStringValue(row, 1));

        return book;
    }

    private Review parseReview(List<Object> row) {
        String reviewText = getStringValue(row, 4);
        int rating = getIntegerValue(row, 5);

        Review review = new Review();
        review.setReview(reviewText);
        review.setRating(rating);

        return review;
    }

    private Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        employee.setFirstname(getStringValue(row, 2));
        employee.setLastname(getStringValue(row, 3));
        employee.setId(employee.hashCode());

        return employee;
    }
}
