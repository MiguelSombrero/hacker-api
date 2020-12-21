package com.hacker.api.utils;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Author;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GoogleSheetsToBooksTransformer {
    private static Logger logger = LoggerFactory.getLogger(GoogleSheetsToBooksTransformer.class);

    public static Collection<Book> transform(List<List<Object>> values) {
        Collection<Book> books = values.stream()
                .map(row -> bookMapper(row))
                .collect(Collectors.groupingBy(Book::hashCode, Collectors.reducing(null, bookReducer())))
                .values();

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

    private static BinaryOperator<Book> bookReducer() {
        BinaryOperator<Book> reducer = (a, b) -> Optional.ofNullable(a)
                .map(current -> {
                    current.getReviews().addAll(b.getReviews());
                    return current;
                })
                .orElseGet(() -> new Book(b.getName(), b.getAuthor(), new ArrayList<>(b.getReviews())));

        return reducer;
    }

    private static Author parseAuthor(List<Object> row) {
        String names = (String) row.get(0);
        String[] parts = names.split(",");

        Author author = new Author();
        author.setFirstname(parts[1]);
        author.setLastname(parts[0]);

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

        return review;
    }

    private static Employee parseEmployee(List<Object> row) {
        Employee employee = new Employee();
        employee.setFirstname((String) row.get(2));
        employee.setLastname((String) row.get(3));

        return employee;
    }
}
