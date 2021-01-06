package com.hacker.api.parsers;

import com.hacker.api.domain.books.VisualBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SheetToVisualBookParserTest {

    @Autowired
    private SheetToVisualBooksParser visualBooksParser;

    @Test
    public void parseBooksWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBookNameIsCaseInsensitive() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
    }

    @Test
    public void parseBooksWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "kolkyt", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "neljä")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(0, book.getPages());
    }

    @Test
    public void parseBooksWhenBookTypeNotFound() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("UNDEFINED", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBooksWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "", "Suosittelen kaikille", "")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseBooksWhenEmailIsNotFirstnameDotLastname() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miikasomero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) visualBooksParser.parse(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miikasomero", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

}
