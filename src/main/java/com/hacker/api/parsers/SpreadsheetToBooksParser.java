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
        String[] parts = getAuthorsNames(row).split(";");

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
        book.setName(getBookName(row));

        return book;
    }

    private Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setReview(getBookReview(row));
        review.setRating(getBookRating(row));

        return review;
    }

    private Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        employee.setFirstname(getEmployeeFirstname(row));
        employee.setLastname(getEmployeeLastname(row));
        employee.setId(employee.hashCode());

        return employee;
    }

    private String getAuthorsNames(List<Object> row) {
        return parseStringValue(row, 0);
    }

    private String getBookName(List<Object> row) {
        return parseStringValue(row, 1);
    }

    private String getEmployeeFirstname(List<Object> row) {
        return parseStringValue(row, 2);
    }

    private String getEmployeeLastname(List<Object> row) {
        return parseStringValue(row, 3);
    }

    private String getBookReview(List<Object> row) {
        return parseStringValue(row, 4);
    }

    private int getBookRating(List<Object> row) {
        return parseIntegerValue(row, 5);
    }
}
