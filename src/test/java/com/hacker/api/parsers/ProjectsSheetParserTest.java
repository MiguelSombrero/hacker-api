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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProjectsSheetParserTest {

    private final static int expectedSkillListSize = 3;

    @Autowired
    private ProjectsSheetParser projectsSheetParser;

    @Test
    public void parseHackerWhenAllFieldsAreCorrect() {
        Hacker hacker = createDefaultHacker();
        assertHacker(hacker);
    }

    private Hacker createDefaultHacker(){
        List<Object> projectsSheet = createDefaultObjectList("Alfame", "Toteutus");
        Hacker hacker = projectsSheetParser.parseProjectHacker(projectsSheet);
        return hacker;
    }

    private void assertHacker(Hacker hacker){
        assertEquals("Miika", hacker.getFirstName());
        assertEquals("Somero", hacker.getLastName());
    }

    @Test
    public void parseSkillsWhenAllFieldsAreCorrect() {
        List<Skill> skills = createDefaultSkills();
        int experienceMonths = 4;
        assertSkills(skills, experienceMonths);
    }

    @Test
    public void parseSkillsWhenSkillsEmpty() {
        List<Object> projectsSheet = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Koodaus", "8/2020", "11/2020", "Alfame", "", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<Skill> skills = projectsSheetParser.parseSkills(projectsSheet);
        assertTrue(skills.isEmpty());
    }

    private List<Skill> createDefaultSkills(){
        List<Object> projectsSheet = createDefaultObjectList("Alfame", "Toteutus");
        List<Skill> skills = projectsSheetParser.parseSkills(projectsSheet);
        return skills;
    }

    @Test
    public void parseProjectWhenAllFieldsAreCorrect() {
        List<Object> projectsSheet = createDefaultObjectList("Kela", "Toteutus");
        Project project = projectsSheetParser.parseProject(projectsSheet);
        assertProject(project, projectsSheet);
    }

    private void assertProject(Project project, List<Object> projectsSheet){
        assertEquals("Verkkokauppa", project.getName());
        assertEquals("Kela", project.getClient());
        assertEquals("Verkkokaupan toteutus", project.getDescription());
        assertEquals("Alfame", project.getEmployer());
        assertEquals("2020-08-01", project.getStart().toString());
        assertEquals("2020-11-01", project.getEnd().toString());
    }

    @Test
    public void parseRoleWhenAllFieldsAreCorrect() {
        List<Object> projectsSheet = createDefaultObjectList("Kela", "Toteutus, Määrittely");

        Role role = projectsSheetParser.parseRole(projectsSheet);
        assertRole(role);
    }

    private void assertRole(Role role){
        assertEquals("Sovelluskehittäjä", role.getName());
        assertEquals(2, role.getTasks().size());
        assertEquals("Toteutus", role.getTasks().get(0));
        assertEquals("Määrittely", role.getTasks().get(1));
    }

    private List<Object> createDefaultObjectList(String company, String task){
        List<Object> projectSheet = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", task, "8/2020", "11/2020", company, "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        return projectSheet;
    }

    @Test
    public void calculatesProjectDurationRightWhenOneMonthProject() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/2020", "8/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths = 1;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenTwoMonthProject() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/2020", "9/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths = 2;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenEndsLastDayOfMonth() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/1/2020", "10/31/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths = 3;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenStartsAndEndsMiddleOfMonth() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/19/2020", "11/04/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths = 4;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenOverYearProject() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/2018", "12/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        assertSkills(skills, 29);
    }

    @Test
    public void parseWhenStartDateIsMissing() {
        List<Object> projectSheet = createObjectListForTimePeriod("", "11/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths=0;
        assertSkills(skills, experienceInMonths);
    }

    @Test
    public void parseWhenEndDateIsMissing() {
        List<Object> projectSheet = createObjectListForTimePeriod("8/2020", "");
        List<Skill> skills = projectsSheetParser.parseSkills(projectSheet);

        int experienceInMonths = Period.between(LocalDate.of(2020, 8, 1), LocalDate.now())
                .plusMonths(1)
                .getMonths();

        assertSkills(skills, experienceInMonths);
    }

    private List<Object> createObjectListForTimePeriod(String startDate, String endDate){
        List<Object> projectSheet = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", startDate, endDate, "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        return projectSheet;
    }

    private void assertSkills(List<Skill> skills, int knowHow){
        assertEquals(expectedSkillListSize, skills.size());
        for (Skill skill : skills){
            assertEquals(knowHow, skill.getKnowHowMonths());
        }
    }
}
