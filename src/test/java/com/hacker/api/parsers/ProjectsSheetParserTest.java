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

    private final static int expectedSkillListSize = 3;

    @Autowired
    private ProjectsSheetParser projectsSheetParser;

    @Test
    public void parseHackerWhenAllFieldsAreCorrect() {
        Hacker hacker = createDefaultHacker();
        assertHacker(hacker);
    }


    private Hacker createDefaultHacker(){
        List<Object> row = createDefaultObjectList("Alfame", "Toteutus");
        Hacker hacker = projectsSheetParser.parseProjectHacker(row);
        return hacker;
    }

    private void assertHacker(Hacker hacker){
        assertEquals("Miika", hacker.getFirstname());
        assertEquals("Somero", hacker.getLastname());
    }

    @Test
    public void parseSkillsWhenAllFieldsAreCorrect() {
        List<Skill> skills = createDefaultSkills();
        int experienceMonths = 4;
        assertSkills(skills, experienceMonths);
    }

    private List<Skill> createDefaultSkills(){
        List<Object> row = createDefaultObjectList("Alfame", "Toteutus");
        List<Skill> skills = projectsSheetParser.parseSkills(row);
        return skills;
    }



    @Test
    public void parseProjectWhenAllFieldsAreCorrect() {
        List<Object> row = createDefaultObjectList("Kela", "Toteutus");
        Project project = projectsSheetParser.parseProject(row);
        assertProject(project, row);
    }

    private void assertProject(Project project, List<Object> row){
        assertEquals("Verkkokauppa", project.getName());
        assertEquals("Kela", project.getClient());
        assertEquals("Verkkokaupan toteutus", project.getDescription());
        assertEquals("Alfame", project.getEmployer());
        assertEquals("2020-08-01", project.getStart().toString());
        assertEquals("2020-11-01", project.getEnd().toString());
    }

    @Test
    public void parseRoleWhenAllFieldsAreCorrect() {
        List<Object> row = createDefaultObjectList("Kela", "Toteutus, Määrittely");

        Role role = projectsSheetParser.parseRole(row);
        assertRole(role);
    }

    private void assertRole(Role role){
        assertEquals("Sovelluskehittäjä", role.getName());
        assertEquals(2, role.getTasks().size());
        assertEquals("Toteutus", role.getTasks().get(0));
        assertEquals("Määrittely", role.getTasks().get(1));
    }

    private List<Object> createDefaultObjectList(String company, String task){
        List<Object> hackerProjectDescriptionRows = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", task, "8/1/2020", "11/1/2020", company, "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        return hackerProjectDescriptionRows;
    }

    @Test
    public void calculatesProjectDurationRightWhenOneMonthProject() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/1/2020", "8/1/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths = 1;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenTwoMonthProject() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/1/2020", "9/1/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths = 2;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenEndsLastDayOfMonth() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/1/2020", "10/31/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths = 3;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenStartsAndEndsMiddleOfMonth() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/19/2020", "11/04/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths = 4;
        assertSkills(skills,experienceInMonths);
    }

    @Test
    public void calculatesProjectDurationRightWhenOverYearProject() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/1/2018", "12/1/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        assertSkills(skills, 29);
    }

    @Test
    public void parseWhenStartDateIsMissing() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("", "11/1/2020");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths=0;
        assertSkills(skills, experienceInMonths);
    }

    @Test
    public void parseWhenEndDateIsMissing() {
        List<Object> hackerProjectDescriptionRows = createObjectListForTimePeriod("8/1/2020", "");
        List<Skill> skills = projectsSheetParser.parseSkills(hackerProjectDescriptionRows);

        int experienceInMonths = Period.between(LocalDate.of(2020, 8, 1), LocalDate.now())
                .plusMonths(1)
                .getMonths();

        assertSkills(skills, experienceInMonths);
    }

    private List<Object> createObjectListForTimePeriod(String startDate, String endDate){
        List<Object> hackerProjectDescriptionRows = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", startDate, endDate, "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        return hackerProjectDescriptionRows;
    }

    private void assertSkills(List<Skill> skills, int knowHow){
        assertEquals(expectedSkillListSize, skills.size());
        for (Skill skill : skills){
            assertEquals(knowHow, skill.getKnowHowMonths());
        }
    }
}
