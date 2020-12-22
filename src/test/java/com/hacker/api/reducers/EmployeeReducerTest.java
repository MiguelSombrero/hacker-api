package com.hacker.api.reducers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeReducerTest {

    @Autowired
    private EmployeeReducer employeeReducer;

    @Test
    public void testMerge() {
        Skill skill1 = new Skill();
        skill1.setName("Java");
        skill1.setKnowHowMonths(5);

        Skill skill2 = new Skill();
        skill2.setName("Java");
        skill2.setKnowHowMonths(2);

        Skill skill3 = new Skill();
        skill3.setName("React");
        skill3.setKnowHowMonths(5);

        Skill skill4 = new Skill();
        skill4.setName("Java");
        skill4.setKnowHowMonths(11);

        Skill skill5 = new Skill();
        skill5.setName("XML");
        skill5.setKnowHowMonths(8);

        Skill skill6 = new Skill();
        skill6.setName("XML");
        skill6.setKnowHowMonths(2);

        Employee employee1 = new Employee();
        employee1.setFirstname("Miika");
        employee1.setLastname("Somero");
        employee1.getSkills().add(skill1);
        employee1.getSkills().add(skill2);
        employee1.getSkills().add(skill3);

        Employee employee2 = new Employee();
        employee2.setFirstname("Miika");
        employee2.setLastname("Somero");
        employee2.getSkills().add(skill4);
        employee2.getSkills().add(skill5);
        employee2.getSkills().add(skill6);

        Employee result = employeeReducer.merge(employee1, employee2);

        assertEquals("Miika", result.getFirstname());
        assertEquals("Somero", result.getLastname());
        assertEquals(3, result.getSkills().size());
        assertEquals(18, result.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(5, result.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(10, result.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());
    }
}
