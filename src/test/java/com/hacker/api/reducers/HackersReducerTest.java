package com.hacker.api.reducers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HackersReducerTest {
    protected static Logger logger = LoggerFactory.getLogger(HackersReducerTest.class);

    @Autowired
    private HackersReducer hackersReducer;

    @Test
    public void testMerge() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 5);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);
        Skill skill3 = DomainObjectFactory.getSkill("React", 5);
        Skill skill4 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill5 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill6 = DomainObjectFactory.getSkill("XML", 2);

        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker1.getSkills().add(skill1);
        hacker1.getSkills().add(skill2);
        hacker1.getSkills().add(skill3);

        Hacker hacker2 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker2.getSkills().add(skill4);
        hacker2.getSkills().add(skill5);
        hacker2.getSkills().add(skill6);

        Hacker result = hackersReducer.merge(hacker1, hacker2);

        assertEquals("Miika", result.getFirstname());
        assertEquals("Somero", result.getLastname());
        assertEquals(3, result.getSkills().size());
        assertEquals(18, result.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(5, result.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(10, result.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());
    }

    @Test
    public void testReduce() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 5);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);
        Skill skill3 = DomainObjectFactory.getSkill("React", 5);
        Skill skill4 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill5 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill6 = DomainObjectFactory.getSkill("XML", 2);

        Skill skill7 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill8 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill9 = DomainObjectFactory.getSkill("XML", 2);

        Skill skill10 = DomainObjectFactory.getSkill("XML", 8);

        Project project1 = DomainObjectFactory.getProject("Verkkokauppa");
        Project project2 = DomainObjectFactory.getProject("Karkkikauppa");
        Project project3 = DomainObjectFactory.getProject("IDM");
        Project project4 = DomainObjectFactory.getProject("Ohjausjärjestelmä");
        Project project5 = DomainObjectFactory.getProject("Elektroninen lukitusjärjestelmä");

        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker1.getProjects().add(project1);
        hacker1.getSkills().add(skill1);
        hacker1.getSkills().add(skill2);
        hacker1.getSkills().add(skill3);

        Hacker hacker3 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker3.getProjects().add(project3);
        hacker3.getSkills().add(skill4);

        Hacker hacker4 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker4.getProjects().add(project4);
        hacker4.getSkills().add(skill5);
        hacker4.getSkills().add(skill6);

        Hacker hacker2 = DomainObjectFactory.getEmployee("Siiri", "Siirinen");
        hacker2.getProjects().add(project2);
        hacker2.getProjects().add(project4);
        hacker2.getSkills().add(skill7);
        hacker2.getSkills().add(skill8);

        Hacker hacker6 = DomainObjectFactory.getEmployee("Siiri", "Siirinen");
        hacker6.getSkills().add(skill9);

        Hacker hacker5 = DomainObjectFactory.getEmployee("Jussi", "Jussinen");
        hacker5.getProjects().add(project5);
        hacker5.getSkills().add(skill10);

        Map<Integer, Hacker> hackers = Arrays.asList(hacker1, hacker2, hacker3, hacker4, hacker5, hacker6).stream()
                .collect(groupingBy(Hacker::getId, reducing(null, hackersReducer.reduce())));

        List<Hacker> result = new ArrayList<>(hackers.values());

        Hacker miika = result.stream()
                .filter(hacker -> hacker.getFirstname().equals("Miika"))
                .findFirst().get();

        Hacker siiri = result.stream()
                .filter(hacker -> hacker.getFirstname().equals("Siiri"))
                .findFirst().get();

        Hacker jussi = result.stream()
                .filter(hacker -> hacker.getFirstname().equals("Jussi"))
                .findFirst().get();

        assertEquals(3, result.size());

        assertEquals("Miika", miika.getFirstname());
        assertEquals("Somero", miika.getLastname());
        assertEquals(3, miika.getSkills().size());
        assertEquals(3, miika.getProjects().size());
        assertEquals(18, miika.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(5, miika.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(10, miika.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());

        assertEquals("Siiri", siiri.getFirstname());
        assertEquals("Siirinen", siiri.getLastname());
        assertEquals(2, siiri.getSkills().size());
        assertEquals(2, siiri.getProjects().size());
        assertEquals(11, siiri.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(10, siiri.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());

        assertEquals("Jussi", jussi.getFirstname());
        assertEquals("Jussinen", jussi.getLastname());
        assertEquals(1, jussi.getSkills().size());
        assertEquals(1, jussi.getProjects().size());
        assertEquals(8, jussi.getSkills().stream().filter(skill -> skill.getName().equals("XML")).findFirst().get().getKnowHowMonths());
    }
}
