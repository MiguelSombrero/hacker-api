package com.hacker.api.parsers;

import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CoursesParser extends SheetParserImpl {

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

    private int parseDuration(String duration) {
        int hours = 0;
        int minutes = 0;

        try {
            String[] parts = duration.split(":");
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse duration"));
        } catch (NumberFormatException e) {
            logger.info(String.format("Could not parse duration"));
        }

        return hours * 60 + minutes;
    }

    public boolean isWebCourse(List<Object> studiesSheet) {
        return isOfType(studiesSheet, "Verkkokurssibonus");
    }

    private boolean isOfType(List<Object> studiesSheet, String type) {
        String value = getStudyType(studiesSheet).toLowerCase();

        if (!value.isEmpty() && value.equals(type.toLowerCase())) {
            return true;
        }

        return false;
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
