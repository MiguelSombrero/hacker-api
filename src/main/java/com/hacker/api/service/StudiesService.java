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
import java.util.Collection;
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
                .filter(this::isBook)
                .map(this::parseBookFromRow)
                .collect(groupingBy(Book::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedBooks = calculateRatingAndReturnSorted(books.values());

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

    public List<Rateable> getCourses() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> courses = values.stream()
                .filter(this::isWebCourse)
                .map(this::parseCourseFromRow)
                .collect(groupingBy(Course::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedCourses = calculateRatingAndReturnSorted(courses.values());

        return sortedCourses;
    }

    private List<Rateable> calculateRatingAndReturnSorted(Collection<Rateable> reviewable) {
        List<Rateable> sorted = reviewable.stream()
                .map(item -> {
                    item.setRating(item.calculateRating());
                    return item;
                })
                .sorted()
                .collect(Collectors.toList());

        return sorted;
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
