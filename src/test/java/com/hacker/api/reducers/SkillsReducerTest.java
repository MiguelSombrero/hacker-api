package com.hacker.api.reducers;

import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SkillsReducerTest {

    @Autowired
    private SkillsReducer skillsReducer;

    @Test
    public void testMerge() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);

        Skill skill = skillsReducer.merge(skill1, skill2);

        assertEquals("Java", skill.getName());
        assertEquals(12, skill.getKnowHowMonths());
    }

    @Test
    public void testReduce() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 5);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);
        Skill skill3 = DomainObjectFactory.getSkill("React", 5);
        Skill skill4 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill5 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill6 = DomainObjectFactory.getSkill("XML", 2);

        Map<Integer, Skill> skills = Arrays.asList(skill1, skill2, skill3, skill4, skill5, skill6).stream()
                .collect(groupingBy(Skill::getId, reducing(null, skillsReducer.reduce())));

        List<Skill> result = new ArrayList<>(skills.values());

        Skill java = result.stream()
                .filter(skill -> skill.getName().equals("Java"))
                .findFirst().get();

        Skill react = result.stream()
                .filter(skill -> skill.getName().equals("React"))
                .findFirst().get();

        Skill xml = result.stream()
                .filter(skill -> skill.getName().equals("XML"))
                .findFirst().get();

        assertEquals(3, result.size());

        assertEquals("Java", java.getName());
        assertEquals(18, java.getKnowHowMonths());

        assertEquals("React", react.getName());
        assertEquals(5, react.getKnowHowMonths());

        assertEquals("XML", xml.getName());
        assertEquals(10, xml.getKnowHowMonths());
    }
}
