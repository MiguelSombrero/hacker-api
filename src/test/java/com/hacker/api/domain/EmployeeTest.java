package com.hacker.api.domain;

import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeTest extends BaseDomainTest {

    @Test
    public void ifSameFirstnameAndLastnameEmployeeIsSame() {
        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Employee employee2 = DomainObjectFactory.getEmployee("Miika", "Somero");

        isSame(employee1, employee2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentSkillsEmployeeIsSame() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee1.getSkills().add(skill1);

        Skill skill2 = DomainObjectFactory.getSkill("React", 1);
        Employee employee2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee2.getSkills().add(skill2);

        isSame(employee1, employee2);
    }

    @Test
    public void ifSameFirstnameAndLastnameAndDifferentProjectsEmployeeIsSame() {
        Project project1 = DomainObjectFactory.getProject("Eläkeuudistus 2017");
        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee1.getProjects().add(project1);

        Project project2 = DomainObjectFactory.getProject("Verkkokauppa");
        Employee employee2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee2.getProjects().add(project2);

        isSame(employee1, employee2);
    }

    @Test
    public void ifDifferentFirstnameEmployeeIsDifferent() {
        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Employee employee2 = DomainObjectFactory.getEmployee("Jukka", "Somero");

        isDifferent(employee1, employee2);
    }

    @Test
    public void ifDifferentLastnameEmployeeIsDifferent() {
        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        Employee employee2 = DomainObjectFactory.getEmployee("Miika", "Lahtinen");

        isDifferent(employee1, employee2);
    }
}
