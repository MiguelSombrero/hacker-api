package com.hacker.api.reducers;

import com.hacker.api.domain.projects.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillReducer extends ReducerTemplate<Skill> {
    @Override
    protected Skill merge(Skill current, Skill next) {
        Skill skill = new Skill();
        skill.setName(current.getName());
        skill.setKnowHowMonths(current.getKnowHowMonths() + next.getKnowHowMonths());

        return skill;
    }
}
