package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Author;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BooksParser extends SpreadsheetParser<Book> {

    public BooksParser(List<List<Object>> values) {
        super(values);
    }

    protected Book mapToObject(List<Object> row) {
        List<Author> authors = parseAuthors(row);
        Employee reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);

        Book book = parseBook(row);
        book.setAuthors(authors);
        book.getReviews().add(review);

        return book;
    }

    private List<Author> parseAuthors(List<Object> row) {
        String text = getValue(row, 0);
        String[] parts = text.split(";");

        List<Author> authors = Arrays.stream(parts)
                .map(author -> new Author(author.split(",")[0].trim(), author.split(",")[1].trim()))
                .collect(Collectors.toList());

        return authors;
    }

    private Book parseBook(List<Object> row) {
        Book book = new Book();
        book.setName(getValue(row, 1));

        return book;
    }

    private Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setReview(getValue(row, 4));
        review.setRating(Integer.valueOf(getValue(row, 5)));

        return review;
    }

    private Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        employee.setFirstname(getValue(row, 2));
        employee.setLastname(getValue(row, 3));

        return employee;
    }
}
