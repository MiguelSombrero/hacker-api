package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.Course;
import com.hacker.api.domain.studies.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CoursesParserTest {
    private static List<Object> dataRow = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
            .collect(Collectors.toList());

    @Autowired
    private CoursesParser coursesParser;

    @Test
    public void parseWebCourseWhenAllFieldsAreCorrect() {
        Course course = coursesParser.parseWebCourse(dataRow);
        assertEquals("Modern React", course.getName());
        assertEquals(1210, course.getDuration());
    }

    @Test
    public void parseReviewWhenWebCourseRow() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        Review review = coursesParser.parseReview(row);
        assertEquals("Hieno kurssi", review.getReview());
        assertEquals(5, review.getRating());
        assertEquals("2019-06-17T20:11:56", review.getCreated().toString());
    }

    @Test
    public void parseWebCourseReviewWhenAllFieldsAreCorrect() {
        Review review = coursesParser.parseReview(dataRow);
        assertEquals("Hieno kurssi", review.getReview());
        assertEquals(5, review.getRating());
    }

    @Test
    public void parseReviewWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "", "Suosittelen kaikille", "")
                .collect(Collectors.toList());

        Review review = coursesParser.parseReview(row);

        assertEquals("", review.getReview());
        assertEquals(0, review.getRating());
    }

    @Test
    public void recognizesCorrectlyWebCourse() {
        List<Object> row1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> row2 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        assertTrue(coursesParser.isWebCourse(row1));
        assertTrue(coursesParser.isWebCourse(row2));
    }
}
