package com.hacker.api.service;

import com.hacker.api.client.StudiesSheetClient;
import com.hacker.api.domain.studies.*;
import com.hacker.api.domain.Hacker;
import com.hacker.api.parsers.StudiesSheetParser;
import com.hacker.api.reducers.RateableReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    private RateableReducer rateableReducer;

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    public List<Rateable> getBooks() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> books = values.stream()
                .filter(studiesSheet -> studiesSheetParser.isBook(studiesSheet))
                .map(this::parseBookFromStudiesSheet)
                .collect(groupingBy(Book::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedBooks = Rateable.calculateRatingAndReturnSorted(books.values());

        return sortedBooks;
    }

    public Review addBookReview(Review review) throws IOException {
        AudioBook book = (AudioBook) review.getBook();

        List<List<Object>> values = Arrays.asList(
                Arrays.asList(
                        LocalDateTime.now().toString(),
                        review.getReviewer().getEmail(),
                        "Äänikirjabonus", "", "",
                        book.getName(),
                        book.getDuration(),
                        book.getAuthors(), "",
                        review.getReview(), "",
                        review.getRating()
                )
        );

        List<Object> bookReview = studiesSheetClient.addBookReview(values);
        return parseBookReviewFromStudiesSheet(bookReview);
    }

    public List<Rateable> getCourses() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> courses = values.stream()
                .filter(studiesSheet -> studiesSheetParser.isWebCourse(studiesSheet))
                .map(this::parseCourseFromStudiesSheet)
                .collect(groupingBy(Course::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedCourses = Rateable.calculateRatingAndReturnSorted(courses.values());

        return sortedCourses;
    }

    public List<Review> getBookReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(row -> studiesSheetParser.isBook(row))
                .map(this::parseBookReviewFromStudiesSheet)
                .sorted()
                .collect(Collectors.toList());

        return reviews;
    }

    public List<Review> getCourseReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(row -> studiesSheetParser.isWebCourse(row))
                .map(this::parseCourseReviewFromStudiesSheet)
                .sorted()
                .collect(Collectors.toList());

        return reviews;
    }

    private Book parseBookFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(studiesSheet);
        Review review = studiesSheetParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());
        Book book = studiesSheetParser.parseBook(studiesSheet);
        book.getReviews().add(review);

        return book;
    }

    private Course parseCourseFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(studiesSheet);
        Review review = studiesSheetParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        Course course = studiesSheetParser.parseWebCourse(studiesSheet);
        course.getReviews().add(review);

        return course;
    }

    private Review parseBookReviewFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(studiesSheet);
        Book book = studiesSheetParser.parseBook(studiesSheet);

        Review review = studiesSheetParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        review.setBook(book);

        return review;
    }

    private Review parseCourseReviewFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(studiesSheet);
        Course course = studiesSheetParser.parseWebCourse(studiesSheet);

        Review review = studiesSheetParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        review.setCourse(course);

        return review;
    }
}
