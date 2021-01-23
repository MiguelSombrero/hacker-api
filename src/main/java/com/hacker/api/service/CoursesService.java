package com.hacker.api.service;

import com.hacker.api.client.StudiesSheetClient;
import com.hacker.api.domain.studies.*;
import com.hacker.api.domain.Hacker;
import com.hacker.api.parsers.CoursesParser;
import com.hacker.api.parsers.StudiesHackerParser;
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
public class CoursesService {
    private Logger logger = LoggerFactory.getLogger(CoursesService.class);

    @Autowired
    private StudiesSheetClient studiesSheetClient;

    @Autowired
    private RateableReducer rateableReducer;

    @Autowired
    private CoursesParser coursesParser;

    @Autowired
    private StudiesHackerParser studiesHackerParser;

    public List<Rateable> getCourses() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        Map<Integer, Rateable> courses = values.stream()
                .filter(studiesSheet -> coursesParser.isWebCourse(studiesSheet))
                .map(this::parseCourseFromStudiesSheet)
                .collect(groupingBy(Course::getId, reducing(null, rateableReducer.reduce())));

        List<Rateable> sortedCourses = Rateable.calculateRatingAndReturnSorted(courses.values());

        return sortedCourses;
    }

    public List<Review> getCourseReviews() throws IOException {
        List<List<Object>> values = studiesSheetClient.getStudies();

        List<Review> reviews = values.stream()
                .filter(row -> coursesParser.isWebCourse(row))
                .map(this::parseCourseReviewFromStudiesSheet)
                .sorted()
                .collect(Collectors.toList());

        return reviews;
    }

    private Course parseCourseFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesHackerParser.parseStudiesHacker(studiesSheet);
        Review review = coursesParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        Course course = coursesParser.parseWebCourse(studiesSheet);
        course.getReviews().add(review);

        return course;
    }

    private Review parseCourseReviewFromStudiesSheet(List<Object> studiesSheet) {
        Hacker reviewer = studiesHackerParser.parseStudiesHacker(studiesSheet);
        Course course = coursesParser.parseWebCourse(studiesSheet);

        Review review = coursesParser.parseReview(studiesSheet);
        review.setReviewer(reviewer);
        review.setCourse(course);

        return review;
    }
}
