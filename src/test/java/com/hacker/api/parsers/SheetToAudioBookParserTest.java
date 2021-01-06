package com.hacker.api.parsers;

import com.hacker.api.domain.books.AudioBook;
import com.hacker.api.domain.books.VisualBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SheetToAudioBookParserTest {

    @Autowired
    private SheetToAudioBooksParser audioBooksParser;

    @Test
    public void parseBooksWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(3, book.getReviews().get(0).getRating());
        assertEquals("Hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseBooksWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "kuussataa", "Takanen, Kimmo", "", "Hyvä kirja", "", "kolme")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("Hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseBooksWhenBookDurationIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(3, book.getReviews().get(0).getRating());
        assertEquals("Hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseBooksWhenBookDurationIsWrongFormat() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10.08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(3, book.getReviews().get(0).getRating());
        assertEquals("Hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseBooksWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "", "", "")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseBooksAllFieldsAreMissingNotRelevantToAudioBook() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(3, book.getReviews().get(0).getRating());
        assertEquals("Hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

}
