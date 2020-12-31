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
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "Audible", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "2019", "6", "10", "8", "608")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseBooksWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "Audible", "Ihan hyvä kirja", "Suosittelen kaikille", "neljä", "2019", "6", "10", "8", "kuusisataa")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseBooksWhenBookDurationIsMissing() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "Audible", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "2019", "6", "10", "8", "")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseBooksWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "Audible", "", "Suosittelen kaikille", "", "2019", "6", "10", "8", "608")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(0, book.getReviews().get(0).getRating());
        assertEquals("", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseBooksAllFieldsAreMissingNotRelevantToAudioBook() {
        List<Object> row = Stream.of("6/18/2019 14:04:36", "miika.somero@gmail.com", "", "", "", "Tunne Lukkosi", "", "Takanen, Kimmo", "", "Ihan hyvä kirja", "", "4", "", "", "", "", "608")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) audioBooksParser.parse(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Ihan hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("somero", book.getReviews().get(0).getReviewer().getLastname());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

}
