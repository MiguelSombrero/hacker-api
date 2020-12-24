package com.hacker.api.utils;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Author;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import com.hacker.api.reducers.BookReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GoogleSheetsToBooksTransformer {
    private static Logger logger = LoggerFactory.getLogger(GoogleSheetsToBooksTransformer.class);

    @Autowired
    private BookReducer reducer;

    public Collection<Book> transform(List<List<Object>> values) {
        Collection<Book> books = values.stream()
                .map(row -> bookMapper(row))
                .collect(Collectors.groupingBy(Book::hashCode, Collectors.reducing(null, reducer.reduce())))
                .values();

        books.stream().forEach(book -> book.calculateRating());

        return books;
    }

    private static Book bookMapper(List<Object> row) {
        Author author = parseAuthor(row);
        Employee reviewer = parseEmployee(row);

        Review review = parseReview(row);
        review.setReviewer(reviewer);

        Book book = parseBook(row);
        book.setAuthor(author);
        book.getReviews().add(review);

        return book;
    }

    private static Author parseAuthor(List<Object> row) {
        String names = (String) row.get(0);
        String[] parts = names.split(",");

        Author author = new Author();
        author.setFirstname(parts[1].trim());
        author.setLastname(parts[0].trim());

        return author;
    }

    private static Book parseBook(List<Object> row) {
        Book book = new Book();
        book.setName((String) row.get(1));

        return book;
    }

    private static Review parseReview(List<Object> row) {
        Review review = new Review();
        review.setReview((String) row.get(4));
        review.setRating(Integer.valueOf((String) row.get(5)));

        return review;
    }

    private static Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        employee.setFirstname((String) row.get(2));
        employee.setLastname((String) row.get(3));

        return employee;
    }
}
