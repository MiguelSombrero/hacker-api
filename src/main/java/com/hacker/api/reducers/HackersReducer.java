package com.hacker.api.reducers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class HackersReducer extends ReducerTemplate<Hacker> {

    @Autowired
    private SkillsReducer skillsReducer;

    @Override
    protected Hacker merge(Hacker current, Hacker next) {
        current.getSkills().addAll(next.getSkills());
        current.getProjects().addAll(next.getProjects());

        Collection<Skill> skills = skillsReducer.reduce(current.getSkills());

        current.setSkills(skills.stream().collect(Collectors.toList()));

        return current;
    }

    @Override
    protected int getId(Hacker hacker) {
        return hacker.getId();
    }
}
