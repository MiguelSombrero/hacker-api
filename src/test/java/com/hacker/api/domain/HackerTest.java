package com.hacker.api.domain;

import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HackerTest extends BaseDomainTest {

    @Test
    public void ifSameFirstnameAndLastnameEmployeeIsSame() {

        Hacker hacker1 = createHackerObject();
        Hacker hacker2 = createHackerObject();
        isSame(hacker1, hacker2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentSkillsEmployeeIsSame() {

        Hacker hacker1=createHackerWithSkill("Java", 10);
        Hacker hacker2=createHackerWithSkill("React", 1);
        isSame(hacker1, hacker2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentProjectsEmployeeIsSame() {

        Hacker hacker1=createHackerWithProject("Eläkeuudistus 2017");
        Hacker hacker2=createHackerWithProject( "Verkkokauppa");
        isSame(hacker1, hacker2);
    }

    private Hacker createHackerWithSkill(String skill, int knowHowMonths ){

            Skill newSkill = DomainObjectFactory.getSkill(skill, knowHowMonths);
            Hacker hacker = DomainObjectFactory.getEmployee("Miika", "Somero");
            List<Skill> hackerSkills=hacker.getSkills();
            hackerSkills.add(newSkill);
            return hacker;
    }

    private Hacker createHackerWithProject(String projectName){

        Project newProject = DomainObjectFactory.getProject(projectName);
        Hacker hacker = DomainObjectFactory.getEmployee("Miika", "Somero");
        List<Project> hackerProjects= hacker.getProjects();
        hackerProjects.add(newProject);
        return hacker;
    }

    @Test
    public void ifDifferentFirstnameEmployeeIsDifferent() {
        Hacker hacker1 = createHackerObject();
        Hacker hacker2 = createHackerObject("Jukka", "Somero");
        isDifferent(hacker1, hacker2);
    }

    @Test
    public void ifDifferentLastnameEmployeeIsDifferent() {
        Hacker hacker1 = createHackerObject();
        Hacker hacker2 = createHackerObject("Miika", "Lahtinen");
        isDifferent(hacker1, hacker2);
    }

    private Hacker createHackerObject(){
        Hacker hacker = DomainObjectFactory.getEmployee("Miika", "Somero");
        return hacker;
    }

    private Hacker createHackerObject(String firstName, String lastName){
        Hacker hacker = DomainObjectFactory.getEmployee(firstName, lastName);
        return hacker;
    }
}
