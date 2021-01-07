package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.books.AudioBook;
import com.hacker.api.domain.books.Review;
import com.hacker.api.domain.books.VisualBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class StudiesSheetParserTest {

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    @Test
    public void parseStudiesHackerWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        Hacker hacker = studiesSheetParser.parseStudiesHacker(row);

        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
    }

    @Test
    public void parseStudiesHackerWhenEmailIsNotFirstnameDotLastname() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miikasomero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        Hacker hacker = studiesSheetParser.parseStudiesHacker(row);

        assertEquals("Miikasomero", hacker.getFirstname());
        assertEquals("", hacker.getLastname());
    }


    @Test
    public void parseAudioBookWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) studiesSheetParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(608, book.getDuration());
    }

    @Test
    public void parseVisualBookWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) studiesSheetParser.parseVisualBook(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseVisualBookNameIsCaseInsensitive() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) studiesSheetParser.parseVisualBook(row);
        assertEquals("Yksikkötestaus", book.getName());
    }

    @Test
    public void parseAudioBookWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "kuussataa", "Takanen, Kimmo", "", "Hyvä kirja", "", "kolme")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) studiesSheetParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseVisualBookWhenThereIsTextInNumberFields() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "kolkyt", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "neljä")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) studiesSheetParser.parseVisualBook(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("EBOOK", book.getType().toString());
        assertEquals(0, book.getPages());
    }

    @Test
    public void parseAudioBookWhenBookDurationIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) studiesSheetParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseVisualBooksWhenBookTypeNotFound() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        VisualBook book = (VisualBook) studiesSheetParser.parseVisualBook(row);

        assertEquals("Yksikkötestaus", book.getName());
        assertEquals("Manninen, Olli-Pekka", book.getAuthors());
        assertEquals("UNDEFINED", book.getType().toString());
        assertEquals(30, book.getPages());
    }

    @Test
    public void parseAudioBooksWhenBookDurationIsWrongFormat() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10.08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        AudioBook book = (AudioBook) studiesSheetParser.parseAudioBook(row);

        assertEquals("Tunne Lukkosi", book.getName());
        assertEquals("Takanen, Kimmo", book.getAuthors());
        assertEquals("AUDIO", book.getType().toString());
        assertEquals(0, book.getDuration());
    }

    @Test
    public void parseReviewWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3")
                .collect(Collectors.toList());

        Review review = studiesSheetParser.parseReview(row);

        assertEquals("Hyvä kirja", review.getReview());
        assertEquals(3, review.getRating());
        assertEquals("2019-06-17T20:11:56", review.getCreated().toString());

    }

    @Test
    public void parseReviewWhenReviewAndRatingIsMissing() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "", "Suosittelen kaikille", "")
                .collect(Collectors.toList());

        Review review = studiesSheetParser.parseReview(row);

        assertEquals("", review.getReview());
        assertEquals(0, review.getRating());
        assertEquals("2019-06-17T20:11:56", review.getCreated().toString());
    }
}