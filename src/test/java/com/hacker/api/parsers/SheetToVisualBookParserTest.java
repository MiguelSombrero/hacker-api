package com.hacker.api.parsers;

import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.VisualBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SheetToVisualBookParserTest {

    @Autowired
    private SheetToVisualBooksParser visualBooksParser;

    @Test
    public void parseBooksWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Kirjabonus", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "2019", "6", "2019")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBooksWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Kirjabonus", "Yksikkötestaus", "kolmekymmentä", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "neljä", "2019", "6", "2019")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(0, book.getPages());
    }

    @Test
    public void parseBooksWhenBookTypeNotFound() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Kirjabonus", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "ei ole tällaista tyyppiä", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "2019", "6", "2019")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("UNDEFINED", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBooksWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Kirjabonus", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "", "Suosittelen kaikille", "", "2019", "6", "2019")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBooksWhenEmailIsNotFirstnameDotLastname() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miikasomero@gmail.com", "Kirjabonus", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "2019", "6", "2019")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miikasomero", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

}
