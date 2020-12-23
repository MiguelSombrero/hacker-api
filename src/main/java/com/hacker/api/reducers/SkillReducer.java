package com.hacker.api.reducers;

import com.hacker.api.domain.projects.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillReducer extends ReducerTemplate<Skill> {
    @Override
    protected Skill merge(Skill current, Skill next) {
        current.setKnowHowMonths(current.getKnowHowMonths() + next.getKnowHowMonths());
        return current;
    }
}
