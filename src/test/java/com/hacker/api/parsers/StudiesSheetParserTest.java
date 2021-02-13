package com.hacker.api.parsers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StudiesSheetParserTest {

    @Autowired
    private StudiesSheetParser studiesSheetParser;

    @Test
    public void parseDurationWhenInCorrectForm() {
        int result = studiesSheetParser.parseDuration("10:20");
        assertEquals(620, result);
    }

    @Test
    public void parseDurationReturnsZeroWhenCannotParse() {
        int result = studiesSheetParser.parseDuration("1020");
        assertEquals(0, result);
    }

    @Test
    public void isOfTypeIsCaseInsensitive() {
        List<Object> studiesSheetRow = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "Äänikirjabonus", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
                .collect(Collectors.toList());

        assertTrue(studiesSheetParser.isOfType(studiesSheetRow, "äänikirjabonus"));
    }
}
