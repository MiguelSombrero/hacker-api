package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.studies.Book;
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
public class StudiesServiceTest {
    protected static Logger logger = LoggerFactory.getLogger(StudiesServiceTest.class);

    @MockBean
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private StudiesService studiesService;

    @BeforeEach
    public void setUp() throws IOException {
        // these should be merged as one
        List<Object> visualBook1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> visualBook2 = Stream.of("1/15/2021 20:11:56", "jukka.jukkanen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Voisi olla parempikin", "Njaa", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> visualBook3 = Stream.of("6/22/2019 20:11:56", "mari.marinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihana kirja", "", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        // end of merge

        List<Object> visualBook4 = Stream.of("6/17/2019 20:11:56", "mari.marinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihana kirja", "", "3", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        List<Object> visualBook5 = Stream.of("6/17/2019 20:11:56", "testi.testinen@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Geenin Itsekkyys", "334", "Dawkins, Richard", "Paperiversio", "Ihan huippu kirja!", "Njaa", "2", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        // these should be merged as one
        List<Object> audioBook1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> audioBook2 = Stream.of("6/17/2019 20:11:56", "jukka.jukkanen@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Paras mitä on!", "", "5", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        // end of merge

        List<Object> audioBook3 = Stream.of("6/17/2019 20:11:56", "jukka.jukkanen@testi.fi", "Äänikirjabonus", "", "", "Clean Code", "13:18", "Martin, Robert C.", "", "Klassikko!", "", "5", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        // these should be merged as one
        List<Object> webCourse1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> webCourse2 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Paras React kurssi", "", "4")
                .collect(Collectors.toList());
        // end of merge

        List<Object> webCourse3 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Verkkokurssibonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Scrum mastery", "10:00", "10:00", "No joo", "", "2")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(
                visualBook1, visualBook2, visualBook3, visualBook4, visualBook5,
                audioBook1, audioBook2, audioBook3,
                webCourse1, webCourse2, webCourse3);

        Mockito.when(sheetsClient.getValuesFromSheet(anyString(), anyString()))
                .thenReturn(values);
    }

    @Test
    public void getBooksFilterBooksCorrectly() throws IOException {
        List<Rateable> books = studiesService.getBooks();
        assertEquals(5, books.size());
    }

    @Test
    public void getBooksParsesAndMergesBooksCorrectly() throws IOException {
        List<Rateable> books = studiesService.getBooks();

        Book yksikkotestausPaper = books.stream()
                .map(rateable -> (Book) rateable)
                .filter(book -> filterBookByNameAndType(book, "Yksikkötestaus", "Paperiversio"))
                .findFirst().get();

        assertEquals("Manninen, Olli-Pekka", yksikkotestausPaper.getAuthors());
        assertEquals(3.3, yksikkotestausPaper.getRating());
        assertEquals(3, yksikkotestausPaper.getReviews().size());
        // assert reviews here
    }

    @Test
    public void getBooksSortsBooksCorrectly() throws IOException {
        List<Book> books = studiesService.getBooks()
                .stream().map(rateable -> (Book) rateable)
                .collect(Collectors.toList());

        assertEquals("Clean Code", books.get(0).getName()); // 5
        assertEquals("Tunne Lukkosi", books.get(1).getName()); // 4
        assertEquals("Yksikkötestaus", books.get(2).getName()); // 3 sähköinen
        assertEquals("Yksikkötestaus", books.get(3).getName()); // 3.3 paperi
        assertEquals("Geenin Itsekkyys", books.get(4).getName()); // 2
    }

    @Test
    public void getCoursesFilterCoursesCorrectly() throws IOException {
        List<Rateable> course = studiesService.getCourses();
        assertEquals(2, course.size());
    }

    @Test
    public void getCoursesParsesAndMergesBooksCorrectly() throws IOException {
        List<Rateable> courses = studiesService.getCourses();

        Course modernReact = courses.stream()
                .map(rateable -> (Course) rateable)
                .filter(course -> course.getName().equals("Modern React"))
                .findFirst().get();

        assertEquals(1210, modernReact.getDuration());
        assertEquals(4.5,modernReact.getRating());
        assertEquals(2, modernReact.getReviews().size());
        // assert reviews here
    }

    @Test
    public void getCoursesSortsBooksCorrectly() throws IOException {
        List<Course> courses = studiesService.getCourses()
                .stream().map(rateable -> (Course) rateable)
                .collect(Collectors.toList());

        assertEquals("Modern React", courses.get(0).getName()); // 4.5
        assertEquals("Scrum Mastery", courses.get(1).getName()); // 2
    }

    @Test
    public void getReviewsFilterBooksCorrectly() throws IOException {
        List<Review> review = studiesService.getReviews();
        assertEquals(8, review.size());
    }

    // test review parsing and sorting here

    private boolean filterBookByNameAndType(Book book, String name, String type) {
        return book.getName().equals(name) && book.getType().getName().equals(type);
    }
}