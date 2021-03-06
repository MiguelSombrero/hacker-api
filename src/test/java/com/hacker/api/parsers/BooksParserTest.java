package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.AudioBook;
import com.hacker.api.domain.studies.Review;
import com.hacker.api.domain.studies.VisualBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BooksParserTest {
    private static List<Object> dataRow = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
            .collect(Collectors.toList());

    @Autowired
    private BooksParser booksParser;

    @Test
    public void parseAudioBookWhenAllFieldsAreCorrect() {
        AudioBook book = (AudioBook) booksParser.parseAudioBook(dataRow);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseVisualBookWhenAllFieldsAreCorrect() {
        VisualBook book = (VisualBook) booksParser.parseVisualBook(dataRow);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseVisualBookNameIsCaseInsensitive() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) booksParser.parseVisualBook(row);
        assertEquals("Yksikkötestaus", book.getName());
    }

    @Test
    public void parseAudioBookWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "kuussataa", "Takanen, Kimmo", "", "Hyvä kirja", "", "kolme")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) booksParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseVisualBookWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "kolkyt", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "neljä")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) booksParser.parseVisualBook(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(0, book.getPages());
    }

    @Test
    public void parseAudioBookWhenBookDurationIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) booksParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseVisualBooksWhenBookTypeNotFound() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) booksParser.parseVisualBook(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("UNDEFINED", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseAudioBooksWhenBookDurationIsWrongFormat() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10.08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) booksParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseReviewWhenAudioBookRow() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        Review review = booksParser.parseReview(row);
        assertEquals("Hyvä kirja", review.getReview());
        assertEquals(3, review.getRating());
        assertEquals("2019-06-17T20:11:56", review.getCreated().toString());
    }

    @Test
    public void parseReviewWhenVisualBookRow() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        Review review = booksParser.parseReview(row);
        assertEquals("Ihan hyvä kirja", review.getReview());
        assertEquals(4, review.getRating());
        assertEquals("2019-06-17T20:11:56", review.getCreated().toString());
    }

    @Test
    public void parseAudioBookReviewWhenAllFieldsAreCorrect() {
        Review review = booksParser.parseAudioBookReview(dataRow);
        assertEquals("Hyvä kirja", review.getReview());
        assertEquals(3, review.getRating());
    }

    @Test
    public void parseVisualBookReviewWhenAllFieldsAreCorrect() {
        Review review = booksParser.parseVisualBookReview(dataRow);
        assertEquals("Ihan hyvä kirja", review.getReview());
        assertEquals(4, review.getRating());
    }

    @Test
    public void parseReviewWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "", "Suosittelen kaikille", "")
                .collect(Collectors.toList());

        Review review = booksParser.parseVisualBookReview(row);

        assertEquals("", review.getReview());
        assertEquals(0, review.getRating());
    }

    @Test
    public void recognizesCorrectlyVisualBook() {
        List<Object> row1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> row2 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "kirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        assertTrue(booksParser.isVisualBook(row1));
        assertTrue(booksParser.isBook(row1));
        assertTrue(booksParser.isVisualBook(row2));
        assertTrue(booksParser.isBook(row2));
    }

    @Test
    public void recognizesCorrectlyAudioBook() {
        List<Object> row1 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());
        List<Object> row2 = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "Paperiversio", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        assertTrue(booksParser.isAudioBook(row1));
        assertTrue(booksParser.isBook(row1));
        assertTrue(booksParser.isAudioBook(row2));
        assertTrue(booksParser.isBook(row2));
    }
}
