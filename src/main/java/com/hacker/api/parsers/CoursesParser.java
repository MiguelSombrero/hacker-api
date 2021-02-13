package com.hacker.api.parsers;

import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CoursesParser extends StudiesSheetParser {

    public Course parseWebCourse(List<Object> studiesSheet) {
        String duration = getWebCourseDurationInHHMM(studiesSheet);

        Course course = new Course();
        course.setName(WordUtils.capitalizeFully(getWebCourseName(studiesSheet)));
        course.setDuration(parseDuration(duration));
        course.setId(course.hashCode());

        return course;
    }

    public Review parseReview(List<Object> studiesSheet) {
        Review review = new Review();
        review.setReview(getWebCourseReview(studiesSheet));
        review.setRating(getWebCourseRating(studiesSheet));
        review.setCreated(getTimestamp(studiesSheet));
        review.setId(review.hashCode());

        return review;
    }

    public boolean isWebCourse(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Verkkokurssibonus");
    }

    private LocalDateTime getTimestamp(List<Object> studiesSheet) { return parseDateTimeValue(studiesSheet, 0); }

    public String getStudyType(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 2);
    }

    private String getWebCourseName(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 22);
    }

    private String getWebCourseDurationInHHMM(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 23);
    }

    private String getWebCourseReview(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 25);
    }

    private int getWebCourseRating(List<Object> studiesSheet) {
        return parseIntegerValue(studiesSheet, 27);
    }

}
