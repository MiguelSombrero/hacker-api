package com.hacker.api.domain.projects;

import com.hacker.api.domain.BaseDomainTest;
import com.hacker.api.domain.books.AudioBook;
import com.hacker.api.domain.books.VisualBook;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SkillTest extends BaseDomainTest {

    @Test
    public void ifSameNameSkillIsSame() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 10);

        isSame(skill1, skill2);
    }

    @Test
    public void ifSameNameAndDifferentKnowHowSkillIsSame() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 1);

        isSame(skill1, skill2);
    }

    @Test
    public void ifDifferentNameSkillIsDifferent() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Skill skill2 = DomainObjectFactory.getSkill("React", 10);

        isDifferent(skill1, skill2);
    }

    @Test
    public void ifDifferentNameSkillIsDifferent2() {
        Skill skill1 = DomainObjectFactory.getSkill("Java", 10);
        Skill skill2 = DomainObjectFactory.getSkill("java", 10);

        isDifferent(skill1, skill2);
    }
}
