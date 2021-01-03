package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SheetToHackerParserTest {

    @Autowired
    private SheetToHackerParser sheetToHackerParser;

    @Test
    public void parseWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
        assertEquals(3, hacker.getSkills().size());
        assertEquals(4, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(4, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(4, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenOneMonthProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "8/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals(1, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(1, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(1, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenTwoMonthProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "9/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals(2, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(2, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(2, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenEndsLastDayOfMonth() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "10/31/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals(3, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(3, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(3, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenStartsAndEndsMiddleOfMonth() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/19/2020", "11/04/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals(4, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(4, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(4, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenOverYearProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2018", "12/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals(29, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(29, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(29, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenStartDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
        assertEquals(3, hacker.getSkills().size());
        assertEquals(0, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(0, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(0, hacker.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenEndDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "", "Alfame", "Java,Ansible,React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = (Hacker) sheetToHackerParser.parse(row);

        int knowHow = Period.between(LocalDate.of(2020, 8, 1), LocalDate.now())
                .plusMonths(1)
                .getMonths();

        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
        assertEquals(3, hacker.getSkills().size());
        assertEquals(knowHow, hacker.getSkills().get(0).getKnowHowMonths());
        assertEquals(knowHow, hacker.getSkills().get(1).getKnowHowMonths());
        assertEquals(knowHow, hacker.getSkills().get(2).getKnowHowMonths());
    }
}
