package com.hacker.api.parsers;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SheetParserImplTest {

    @Autowired
    private SheetToVisualBooksParser sheetToVisualBooksParser;

    @Test
    public void parseStringValueWhenStringIsNotEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        String value = sheetToVisualBooksParser.parseStringValue(row, 2);
        assertEquals("Äänikirjabonus", value);
    }

    @Test
    public void parseStringValueWhenStringIsEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        String value = sheetToVisualBooksParser.parseStringValue(row, 3);
        assertEquals("", value);
    }

    @Test
    public void parseStringValueWhenStringContainsBlanks() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "  Tunne Lukkosi  ", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        String value = sheetToVisualBooksParser.parseStringValue(row, 5);
        assertEquals("Tunne Lukkosi", value);
    }

    @Test
    public void parseStringValueWhenStringContainsBlankRows() {
        String review =
                "Hyvä kirja. \n\nVoisi olla parempi.";

        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "  Tunne Lukkosi  ", "10:08", "Takanen, Kimmo", "", review, "", "3"});

        String value = sheetToVisualBooksParser.parseStringValue(row, 9);
        assertEquals(review, value);
    }

    @Test
    public void parseIntegerValueWhenIntegerIsNotEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        int value = sheetToVisualBooksParser.parseIntegerValue(row, 11);
        assertEquals(3, value);
    }

    @Test
    public void parseIntegerValueWhenIntegerIsEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        int value = sheetToVisualBooksParser.parseIntegerValue(row, 3);
        assertEquals(0, value);
    }

    @Test
    public void parseIntegerValueWhenIntegerContainsBlanks() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", " 3 "});

        int value = sheetToVisualBooksParser.parseIntegerValue(row, 3);
        assertEquals(0, value);
    }

    @Test
    public void parseDateValueWhenDateIsNotEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "6/17/2019", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        LocalDate value = sheetToVisualBooksParser.parseDateValue(row, 3);
        assertEquals("2019-06-17", value.toString());
    }

    @Test
    public void parseDateValueReturnCurrentDateWhenDateIsEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "6/17/2019", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        LocalDate now = LocalDate.now();

        LocalDate value = sheetToVisualBooksParser.parseDateValue(row, 4);
        assertEquals(now.toString(), value.toString());
    }

    @Test
    public void parseDateTimeValueWhenDateTimeIsNotEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "6/17/2019", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        LocalDateTime value = sheetToVisualBooksParser.parseDateTimeValue(row, 0);
        assertEquals("2019-06-17T20:11:56", value.truncatedTo(ChronoUnit.SECONDS).toString());
    }

    @Test
    public void parseDateTimeValueReturnCurrentDateTimeWhenDateTimeIsEmpty() {
        List<Object> row = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "6/17/2019", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime value = sheetToVisualBooksParser.parseDateTimeValue(row, 4);
        assertEquals(now.truncatedTo(ChronoUnit.SECONDS).toString(), value.truncatedTo(ChronoUnit.SECONDS).toString());
    }
}
