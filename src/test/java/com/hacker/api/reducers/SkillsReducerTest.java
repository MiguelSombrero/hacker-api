package com.hacker.api.reducers;

import com.hacker.api.domain.projects.Skill;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
