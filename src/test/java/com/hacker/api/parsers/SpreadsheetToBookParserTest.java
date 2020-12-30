package com.hacker.api.parsers;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SpreadsheetToBookParserTest {

    /*@Test
    public void parseWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Dawkins, Richard", "Geenin Itsekkyys", "Miika", "Somero", "Erittäin hyvä kirja", "4")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(row);

        SpreadsheetToBooksParser parser = new SpreadsheetToBooksParser(values);

        List<Book> books = parser.parseBooks();

        Book book = books.get(0);

        assertEquals("Geenin Itsekkyys", book.getName());
        assertEquals("Richard", book.getAuthors().get(0).getFirstname());
        assertEquals("Dawkins", book.getAuthors().get(0).getLastname());
        assertEquals(4, book.getReviews().get(0).getRating());
        assertEquals("Erittäin hyvä kirja", book.getReviews().get(0).getReview());
        assertEquals("Miika", book.getReviews().get(0).getReviewer().getFirstname());
        assertEquals("Somero", book.getReviews().get(0).getReviewer().getLastname());
    }*/

}
