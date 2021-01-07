package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Role;
import com.hacker.api.domain.projects.Skill;
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
public class ProjectsSheetParserTest {

    @Autowired
    private ProjectsSheetParser projectsSheetParser;

    @Test
    public void parseHackerWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Hacker hacker = projectsSheetParser.parseProjectHacker(row);

        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
    }

    @Test
    public void parseSkillsWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(3, skills.size());
        assertEquals(4, skills.get(0).getKnowHowMonths());
        assertEquals(4, skills.get(1).getKnowHowMonths());
        assertEquals(4, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void parseProjectWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Kela", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Project project = projectsSheetParser.parseProject(row);

        assertEquals("Verkkokauppa", project.getName());
        assertEquals("Kela", project.getClient());
        assertEquals("Verkkokaupan toteutus", project.getDescription());
        assertEquals("Alfame", project.getEmployer());
        assertEquals("2020-08-01", project.getStart().toString());
        assertEquals("2020-11-01", project.getEnd().toString());
    }

    @Test
    public void parseRoleWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus, määrittely", "8/1/2020", "11/1/2020", "Kela", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        Role role = projectsSheetParser.parseRole(row);

        assertEquals("Sovelluskehittäjä", role.getName());
        assertEquals(2, role.getTasks().size());
        assertEquals("Toteutus", role.getTasks().get(0));
        assertEquals("Määrittely", role.getTasks().get(1));
    }

    @Test
    public void calculatesProjectDurationRightWhenOneMonthProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "8/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(1, skills.get(0).getKnowHowMonths());
        assertEquals(1, skills.get(1).getKnowHowMonths());
        assertEquals(1, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenTwoMonthProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "9/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(2, skills.get(0).getKnowHowMonths());
        assertEquals(2, skills.get(1).getKnowHowMonths());
        assertEquals(2, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenEndsLastDayOfMonth() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "10/31/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(3, skills.get(0).getKnowHowMonths());
        assertEquals(3, skills.get(1).getKnowHowMonths());
        assertEquals(3, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenStartsAndEndsMiddleOfMonth() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/19/2020", "11/04/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(4, skills.get(0).getKnowHowMonths());
        assertEquals(4, skills.get(1).getKnowHowMonths());
        assertEquals(4, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void calculatesProjectDurationRightWhenOverYearProject() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2018", "12/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(29, skills.get(0).getKnowHowMonths());
        assertEquals(29, skills.get(1).getKnowHowMonths());
        assertEquals(29, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenStartDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        assertEquals(3, skills.size());
        assertEquals(0, skills.get(0).getKnowHowMonths());
        assertEquals(0, skills.get(1).getKnowHowMonths());
        assertEquals(0, skills.get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenEndDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "", "Alfame", "Java,Ansible,React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(row);

        int knowHow = Period.between(LocalDate.of(2020, 8, 1), LocalDate.now())
                .plusMonths(1)
                .getMonths();

        assertEquals(3, skills.size());
        assertEquals(knowHow, skills.get(0).getKnowHowMonths());
        assertEquals(knowHow, skills.get(1).getKnowHowMonths());
        assertEquals(knowHow, skills.get(2).getKnowHowMonths());
    }
}
