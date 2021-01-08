package com.hacker.api.service;

import com.hacker.api.client.StudiesSheetClient;
import com.hacker.api.domain.Course;
import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import com.hacker.api.parsers.StudiesSheetParser;
import com.hacker.api.reducers.BooksReducer;
import com.hacker.api.reducers.CourseReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Service
public class StudiesService {
    private Logger logger = LoggerFactory.getLogger(StudiesService.class);

    @Autowired
    private StudiesSheetClient studiesSheetClient;

    @Autowired
    private BooksReducer booksReducer;

    @Autowired
    private CourseReducer courseReducer;

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    public List<Book> getBooks() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Book> books = values.stream()
                .filter(this::isBook)
                .map(this::parseBookFromRow)
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        List<Book> sortedBooks = books.values().stream()
                .map(book -> {
                    book.setRating(book.calculateRating());
                    return book;
                })
                .sorted()
                .collect(Collectors.toList());

        return sortedBooks;
    }

    public List<Review> getReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(this::isBook)
                .map(this::parseReviewFromRow)
                .sorted()
                .collect(Collectors.toList());

        return reviews;
    }

    public List<Course> getCourses() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Course> courses = values.stream()
                .filter(this::isWebCourse)
                .map(this::parseCourseFromRow)
                .collect(groupingBy(Course::getId, reducing(null, courseReducer.reduce())));

        List<Course> sortedCourses = courses.values().stream()
                .map(course -> {
                    course.setRating(course.calculateRating());
                    return course;
                })
                .sorted()
                .collect(Collectors.toList());

        return sortedCourses;
    }

    private Book parseBookFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);

        Review review = isAudioBook(row) ? studiesSheetParser.parseAudioBookReview(row) : studiesSheetParser.parseVisualBookReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());

        Book book = isAudioBook(row) ? studiesSheetParser.parseAudioBook(row) : studiesSheetParser.parseVisualBook(row);
        book.getReviews().add(review);

        return book;
    }

    private Review parseReviewFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);
        Book book = isAudioBook(row) ? studiesSheetParser.parseAudioBook(row) : studiesSheetParser.parseVisualBook(row);
        Review review = isAudioBook(row) ? studiesSheetParser.parseAudioBookReview(row) : studiesSheetParser.parseVisualBookReview(row);
        review.setBook(book);
        review.setReviewer(reviewer);

        return review;
    }

    private Course parseCourseFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);

        Review review = studiesSheetParser.parseWebCourseReview(row);
        review.setReviewer(reviewer);

        Course course = studiesSheetParser.parseWebCourse(row);
        course.getReviews().add(review);

        return course;
    }

    private boolean isBook(List<Object> row) {
        if (isAudioBook(row) || isVisualBook(row)) {
            return true;
        }

        return false;
    }

    private boolean isAudioBook(List<Object> row) {
        return isOfType(row, "Äänikirjabonus");
    }

    private boolean isVisualBook(List<Object> row) {
        return isOfType(row, "Kirjabonus");
    }

    private boolean isWebCourse(List<Object> row) {
        return isOfType(row, "Verkkokurssibonus");
    }

    private boolean isOfType(List<Object> row, String type) {
        String value = studiesSheetParser.getStudyType(row);

        if (!value.isEmpty() && value.equals(type)) {
            return true;
        }

        return false;
    }
}
