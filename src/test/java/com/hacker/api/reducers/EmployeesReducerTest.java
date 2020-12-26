package com.hacker.api.reducers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeesReducerTest {

    @Autowired
    private EmployeesReducer employeesReducer;

    @Test
    public void testMerge() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 5);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);
        Skill skill3 = DomainObjectFactory.getSkill("React", 5);
        Skill skill4 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill5 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill6 = DomainObjectFactory.getSkill("XML", 2);

        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee1.getSkills().add(skill1);
        employee1.getSkills().add(skill2);
        employee1.getSkills().add(skill3);

        Employee employee2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee2.getSkills().add(skill4);
        employee2.getSkills().add(skill5);
        employee2.getSkills().add(skill6);

        Employee result = employeesReducer.merge(employee1, employee2);

        assertEquals("Miika", result.getFirstname());
        assertEquals("Somero", result.getLastname());
        assertEquals(3, result.getSkills().size());
        assertEquals(18, result.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(5, result.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(10, result.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());
    }
}
