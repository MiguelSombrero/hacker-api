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
    private SheetParserImpl sheetParserImpl;

    @Test
    public void parseStringValueWhenStringIsNotEmpty() {
        List<Object> studiesSheet = createDefaultStudiesSheet();

        String value = sheetParserImpl.parseStringValue(studiesSheet, 2);
        assertEquals("Äänikirjabonus", value);
    }

    @Test
    public void parseStringValueWhenStringIsEmpty() {
        List<Object> studiesSheet = createDefaultStudiesSheet();
        String value = sheetParserImpl.parseStringValue(studiesSheet, 3);
        assertEquals("", value);
    }

    @Test
    public void parseStringValueWhenStringContainsBlanks() {
        List<Object> studiesSheet = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "  Tunne Lukkosi  ", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});

        String value = sheetParserImpl.parseStringValue(studiesSheet, 5);
        assertEquals("Tunne Lukkosi", value);
    }

    @Test
    public void parseStringValueWhenStringContainsBlankRows() {
        String review =
                "Hyvä kirja. \n\nVoisi olla parempi.";

        List<Object> studiesSheet = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "  Tunne Lukkosi  ", "10:08", "Takanen, Kimmo", "", review, "", "3"});

        String value = sheetParserImpl.parseStringValue(studiesSheet, 9);
        assertEquals(review, value);
    }

    @Test
    public void parseIntegerValueWhenIntegerIsNotEmpty() {
        List<Object> studiesSheet = createDefaultStudiesSheet();
        int value = sheetParserImpl.parseIntegerValue(studiesSheet, 11);
        assertEquals(3, value);
    }

    @Test
    public void parseIntegerValueWhenIntegerIsEmpty() {
        List<Object> studiesSheet = createDefaultStudiesSheet();
        int value = sheetParserImpl.parseIntegerValue(studiesSheet, 3);
        assertEquals(0, value);
    }

    private List<Object> createDefaultStudiesSheet(){
        List<Object> studiesSheet = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});
        return studiesSheet;
    }



    @Test
    public void parseIntegerValueWhenIntegerContainsBlanks() {
        List<Object> studiesSheet = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", " 3 "});
        int value = sheetParserImpl.parseIntegerValue(studiesSheet, 3);
        assertEquals(0, value);
    }

    @Test
    public void parseDateValueWhenDateIsNotEmpty() {
        List<Object> studiesSheet = createStudiesSheetWithDate();
        LocalDate value = sheetParserImpl.parseDateValue(studiesSheet, 3);
        assertEquals("2019-06-17", value.toString());
    }

    @Test
    public void parseDateValueReturnCurrentDateWhenDateIsEmpty() {
        List<Object> studiesSheet = createStudiesSheetWithDate();
        LocalDate now = LocalDate.now();

        LocalDate value = sheetParserImpl.parseDateValue(studiesSheet, 4);
        assertEquals(now.toString(), value.toString());
    }

    @Test
    public void parseDateTimeValueWhenDateTimeIsNotEmpty() {
        List<Object> studiesSheet = createStudiesSheetWithDate();
        LocalDateTime value = sheetParserImpl.parseDateTimeValue(studiesSheet, 0);
        assertEquals("2019-06-17T20:11:56", value.truncatedTo(ChronoUnit.SECONDS).toString());
    }

    @Test
    public void parseDateTimeValueReturnCurrentDateTimeWhenDateTimeIsEmpty() {
        List<Object> studiesSheet = createStudiesSheetWithDate();
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime value = sheetParserImpl.parseDateTimeValue(studiesSheet, 4);
        assertEquals(now.truncatedTo(ChronoUnit.SECONDS).toString(), value.truncatedTo(ChronoUnit.SECONDS).toString());
    }

    private List<Object> createStudiesSheetWithDate(){
        List<Object> studiesSheet = Arrays.asList(new String[] {
                "6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "6/17/2019", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3"});
        return studiesSheet;
    }
}
