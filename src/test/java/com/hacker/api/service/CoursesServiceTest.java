package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.studies.Course;
import com.hacker.api.domain.studies.Rateable;
import com.hacker.api.domain.studies.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class CoursesServiceTest {
    protected static Logger logger = LoggerFactory.getLogger(CoursesServiceTest.class);

    @MockBean
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private CoursesService coursesService;

    @BeforeEach
    public void setUp() throws IOException {
        // these should be merged as one
        List<Object> visualBook1 = Stream.of("6/17/2020 20:11:56", "miika.somero@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> visualBook2 = Stream.of("1/15/2021 20:11:56", "jukka.jukkanen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Voisi olla parempikin", "Njaa", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> visualBook3 = Stream.of("6/22/2020 20:11:56", "mari.marinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihana kirja", "", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        // end of merge

        List<Object> visualBook4 = Stream.of("6/17/2019 20:11:56", "mari.marinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihana kirja", "", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        List<Object> visualBook5 = Stream.of("6/17/2019 20:11:56", "testi.testinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Geenin Itsekkyys", "334", "Dawkins, Richard", "Paperiversio", "Ihan huippu kirja!", "Njaa", "2", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        // these should be merged as one
        List<Object> audioBook1 = Stream.of("7/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> audioBook2 = Stream.of("8/17/2019 20:11:56", "jukka.jukkanen@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Paras mitä on!", "", "5", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        // end of merge

        List<Object> audioBook3 = Stream.of("6/17/2019 20:11:56", "jukka.jukkanen@testi.fi", "Äänikirjabonus", "", "", "Clean Code", "13:18", "Martin, Robert C.", "", "Klassikko!", "", "5", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
                .collect(Collectors.toList());

        // these should be merged as one
        List<Object> webCourse1 = Stream.of("6/20/2019 11:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> webCourse2 = Stream.of("6/20/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Paras React kurssi", "", "4")
                .collect(Collectors.toList());
        // end of merge

        List<Object> webCourse3 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "Scrum mastery", "10:00", "10:00", "No joo", "", "2")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(
                visualBook1, visualBook2, visualBook3, visualBook4, visualBook5,
                audioBook1, audioBook2, audioBook3,
                webCourse1, webCourse2, webCourse3);

        Mockito.when(sheetsClient.getValuesFromSheet(anyString(), anyString()))
                .thenReturn(values);
    }

    @Test
    public void getCoursesFilterCoursesCorrectly() throws IOException {
        List<Rateable> course = coursesService.getCourses();
        assertEquals(2, course.size());
    }

    @Test
    public void getCoursesParsesAndMergesBooksCorrectly() throws IOException {
        List<Rateable> courses = coursesService.getCourses();

        Course modernReact = courses.stream()
                .map(rateable -> (Course) rateable)
                .filter(course -> course.getName().equals("Modern React"))
                .findFirst().get();

        assertEquals(1210, modernReact.getDuration());
        assertEquals(4.5,modernReact.getRating());
        assertEquals(2, modernReact.getReviews().size());

        Review review = modernReact.getReviews().stream()
                .filter(rev -> rev.getRating() == 5)
                .findFirst().get();

        assertEquals("2019-06-20T11:11:56", review.getCreated().toString());
        assertEquals("Hieno kurssi", review.getReview());
        assertEquals("Miika", review.getReviewer().getFirstName());
        assertEquals("Somero", review.getReviewer().getLastName());
        assertEquals(null, review.getBook());
        assertEquals(null, review.getCourse());
    }

    @Test
    public void getCoursesSortsCoursesByRating() throws IOException {
        List<Course> courses = coursesService.getCourses()
                .stream().map(rateable -> (Course) rateable)
                .collect(Collectors.toList());

        assertEquals("Modern React", courses.get(0).getName()); // 4.5
        assertEquals("Scrum Mastery", courses.get(1).getName()); // 2
    }

    @Test
    public void getCourseReviewsSortsReviewsByCreated() throws IOException {
        List<Review> reviews = coursesService.getCourseReviews();
        assertEquals("2019-06-20T20:11:56", reviews.get(0).getCreated().toString());
        assertEquals("2019-06-20T11:11:56", reviews.get(1).getCreated().toString());
        assertEquals("2019-06-17T20:11:56", reviews.get(2).getCreated().toString());
    }

    @Test
    public void getCourseReviewsParsesCourseReviewCorrectly() throws IOException {
        List<Review> reviews = coursesService.getCourseReviews();

        Review review = reviews.stream()
                .filter(rev -> rev.getCourse().getName().equals("Scrum Mastery"))
                .findFirst().get();

        assertEquals("No joo", review.getReview());
        assertEquals("Miika", review.getReviewer().getFirstName());
        assertEquals("Somero", review.getReviewer().getLastName());
        assertEquals(null, review.getBook());
    }

}
