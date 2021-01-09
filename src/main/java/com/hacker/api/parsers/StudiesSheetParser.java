package com.hacker.api.parsers;

import com.hacker.api.domain.studies.Course;
import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StudiesSheetParser extends SheetParserImpl {

    public Hacker parseStudiesHacker(List<Object> row) {
        Hacker hacker = new Hacker();
        String firstname = "";
        String lastName = "";

        try {
            String email = getEmail(row);
            String[] parts = email.split("@");
            String[] names = parts[0].split("\\.");

            firstname = WordUtils.capitalizeFully(names[0]);
            lastName = WordUtils.capitalizeFully(names[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse names from row %s", row));
        }

        hacker.setFirstname(firstname);
        hacker.setLastname(lastName);
        hacker.setId(hacker.hashCode());

        return hacker;
    }

    public Book parseBook(List<Object> row) {
        Book book = (isAudioBook(row) ? parseAudioBook(row) : parseVisualBook(row));
        book.setId(book.hashCode());

        return book;
    }

    public Book parseAudioBook(List<Object> row) {
        String duration = getBookDurationInHHMM(row);

        AudioBook book = new AudioBook();
        book.setType(BookType.AUDIO);
        book.setName(WordUtils.capitalizeFully(getAudioBookName(row)));
        book.setAuthors(getAudioBookAuthors(row));
        book.setDuration(parseDuration(duration));

        return book;
    }

    public Book parseVisualBook(List<Object> row) {
        VisualBook book = new VisualBook();
        book.setType(BookType.getBookTypeByTextValue(getBookType(row)));
        book.setName(WordUtils.capitalizeFully(getVisualBookName(row)));
        book.setAuthors(getVisualBookAuthors(row));
        book.setPages(getBookPageCount(row));

        return book;
    }

    public Course parseWebCourse(List<Object> row) {
        String duration = getWebCourseDurationInHHMM(row);

        Course course = new Course();
        course.setName(WordUtils.capitalizeFully(getWebCourseName(row)));
        course.setDuration(parseDuration(duration));
        course.setId(course.hashCode());

        return course;
    }

    public Review parseReview(List<Object> row) {
        Review review = null;

        if (isAudioBook(row)) {
            review = parseAudioBookReview(row);
            review.setBook(parseBook(row));
        } else if (isVisualBook(row)) {
            review = parseVisualBookReview(row);
            review.setBook(parseBook(row));
        } else if (isWebCourse(row)) {
            review = parseWebCourseReview(row);
            review.setCourse(parseWebCourse(row));
        }

        review.setCreated(getTimestamp(row));
        review.setId(review.hashCode());

        return review;
    }

    public Review parseAudioBookReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getAudioBookReview(row));
        review.setRating(getAudioBookRating(row));

        return review;
    }

    public Review parseVisualBookReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getVisualBookReview(row));
        review.setRating(getVisualBookRating(row));

        return review;
    }

    public Review parseWebCourseReview(List<Object> row) {
        Review review = new Review();
        review.setCreated(getTimestamp(row));
        review.setReview(getWebCourseReview(row));
        review.setRating(getWebCourseRating(row));

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

    public boolean isBookOrWebCourse(List<Object> row) {
        return isAudioBook(row) || isVisualBook(row) || isWebCourse(row);
    }

    public boolean isBook(List<Object> row) {
        return isAudioBook(row) || isVisualBook(row);
    }

    public boolean isAudioBook(List<Object> row) {
        return isOfType(row, "Äänikirjabonus");
    }

    public boolean isVisualBook(List<Object> row) {
        return isOfType(row, "Kirjabonus");
    }

    public boolean isWebCourse(List<Object> row) {
        return isOfType(row, "Verkkokurssibonus");
    }

    private boolean isOfType(List<Object> row, String type) {
        String value = getStudyType(row).toLowerCase();

        if (!value.isEmpty() && value.equals(type.toLowerCase())) {
            return true;
        }

        return false;
    }

    private LocalDateTime getTimestamp(List<Object> row) { return parseDateTimeValue(row, 0); }

    private String getEmail(List<Object> row) {
        return parseStringValue(row, 1);
    }

    public String getStudyType(List<Object> row) {
        return parseStringValue(row, 2);
    }

    private String getAudioBookName(List<Object> row) { return parseStringValue(row, 5); }

    private String getBookDurationInHHMM(List<Object> row) {
        return parseStringValue(row, 6);
    }

    private String getAudioBookAuthors(List<Object> row) {
        return parseStringValue(row, 7);
    }

    private String getAudioBookReview(List<Object> row) {
        return parseStringValue(row, 9);
    }

    private int getAudioBookRating(List<Object> row) {
        return parseIntegerValue(row, 11);
    }

    private String getVisualBookName(List<Object> row) {
        return parseStringValue(row, 12);
    }

    private int getBookPageCount(List<Object> row) {
        return parseIntegerValue(row, 13);
    }

    private String getVisualBookAuthors(List<Object> row) {
        return parseStringValue(row, 14);
    }

    private String getBookType(List<Object> row) {
        return parseStringValue(row, 15);
    }

    private String getVisualBookReview(List<Object> row) {
        return parseStringValue(row, 16);
    }

    private int getVisualBookRating(List<Object> row) {
        return parseIntegerValue(row, 18);
    }

    private String getWebCourseName(List<Object> row) {
        return parseStringValue(row, 22);
    }

    private String getWebCourseDurationInHHMM(List<Object> row) {
        return parseStringValue(row, 23);
    }

    private String getWebCourseReview(List<Object> row) {
        return parseStringValue(row, 25);
    }

    private int getWebCourseRating(List<Object> row) {
        return parseIntegerValue(row, 27);
    }

}
