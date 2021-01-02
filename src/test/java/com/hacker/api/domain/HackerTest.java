package com.hacker.api.domain;

import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HackerTest extends BaseDomainTest {

    @Test
    public void ifSameFirstnameAndLastnameEmployeeIsSame() {
        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Hacker hacker2 = DomainObjectFactory.getEmployee("Miika", "Somero");

        isSame(hacker1, hacker2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentSkillsEmployeeIsSame() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker1.getSkills().add(skill1);

        Skill skill2 = DomainObjectFactory.getSkill("React", 1);
        Hacker hacker2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker2.getSkills().add(skill2);

        isSame(hacker1, hacker2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentProjectsEmployeeIsSame() {
        Project project1 = DomainObjectFactory.getProject("Eläkeuudistus 2017");
        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker1.getProjects().add(project1);

        Project project2 = DomainObjectFactory.getProject("Verkkokauppa");
        Hacker hacker2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker2.getProjects().add(project2);

        isSame(hacker1, hacker2);
    }

    @Test
    public void ifDifferentFirstnameEmployeeIsDifferent() {
        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Hacker hacker2 = DomainObjectFactory.getEmployee("Jukka", "Somero");

        isDifferent(hacker1, hacker2);
    }

    @Test
    public void ifDifferentLastnameEmployeeIsDifferent() {
        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Hacker hacker2 = DomainObjectFactory.getEmployee("Miika", "Lahtinen");

        isDifferent(hacker1, hacker2);
    }
}
