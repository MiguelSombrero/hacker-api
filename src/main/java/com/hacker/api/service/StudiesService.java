package com.hacker.api.service;

import com.hacker.api.client.StudiesSheetClient;
import com.hacker.api.domain.studies.Course;
import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.Book;
import com.hacker.api.domain.studies.Review;
import com.hacker.api.domain.studies.Rateable;
import com.hacker.api.parsers.StudiesSheetParser;
import com.hacker.api.reducers.RateableReducer;
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
    private RateableReducer rateableReducer;

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    public List<Rateable> getBooks() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> books = values.stream()
                .filter(row -> studiesSheetParser.isBook(row))
                .map(this::parseBookFromRow)
                .collect(groupingBy(Book::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedBooks = Rateable.calculateRatingAndReturnSorted(books.values());

        return sortedBooks;
    }

    public List<Rateable> getCourses() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> courses = values.stream()
                .filter(row -> studiesSheetParser.isWebCourse(row))
                .map(this::parseCourseFromRow)
                .collect(groupingBy(Course::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedCourses = Rateable.calculateRatingAndReturnSorted(courses.values());

        return sortedCourses;
    }

    public List<Review> getReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(row -> studiesSheetParser.isBookOrWebCourse(row))
                .map(this::parseReviewFromRow)
                .sorted()
                .collect(Collectors.toList());

        return reviews;
    }

    private Book parseBookFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);
        Review review = studiesSheetParser.parseReview(row);
        review.setReviewer(reviewer);
        review.setId(review.hashCode());
        Book book = studiesSheetParser.parseBook(row);
        book.getReviews().add(review);

        return book;
    }

    private Review parseReviewFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);
        Review review = studiesSheetParser.parseReview(row);
        review.setReviewer(reviewer);

        return review;
    }

    private Course parseCourseFromRow(List<Object> row) {
        Hacker reviewer = studiesSheetParser.parseStudiesHacker(row);
        Review review = studiesSheetParser.parseReview(row);
        review.setReviewer(reviewer);
        Course course = studiesSheetParser.parseWebCourse(row);
        course.getReviews().add(review);

        return course;
    }
}
