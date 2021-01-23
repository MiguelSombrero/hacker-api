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
public class StudiesHackerParserTest {
    private static List<Object> dataRow = Stream.of("6/17/2019 20:11:56", "miika.somero@testi.fi", "", "", "", "Tunne Lukkosi", "10:08", "Takanen, Kimmo", "", "Hyvä kirja", "", "3", "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook / sähköinen", "Ihan hyvä kirja", "Suosittelen kaikille", "4", "", "", "", "Modern React", "20:10", "10:05", "Hieno kurssi", "Kaikille", "5")
            .collect(Collectors.toList());

    @Autowired
    private StudiesHackerParser studiesHackerParser;

    @Test
    public void parseStudiesHackerWhenAllFieldsAreCorrect() {
        Hacker hacker = studiesHackerParser.parseStudiesHacker(dataRow);

        assertEquals("Miika", hacker.getFirstName());
        assertEquals("Somero", hacker.getLastName());
    }

    @Test
    public void parseStudiesHackerWhenEmailIsNotFirstnameDotLastname() {
        List<Object> row = Stream.of("6/17/2019 20:11:56", "miikasomero@testi.fi", "", "", "", "", "", "", "", "", "", "",  "Yksikkötestaus", "30", "Manninen, Olli-Pekka", "eBook", "Ihan hyvä kirja", "Suosittelen kaikille", "4")
                .collect(Collectors.toList());

        Hacker hacker = studiesHackerParser.parseStudiesHacker(row);

        assertEquals("Miikasomero", hacker.getFirstName());
        assertEquals("", hacker.getLastName());
    }
}
