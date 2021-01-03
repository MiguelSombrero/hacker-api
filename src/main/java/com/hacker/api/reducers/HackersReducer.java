package com.hacker.api.reducers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@Component
public class HackersReducer extends ReducerTemplate<Hacker> {

    @Autowired
    private SkillsReducer skillsReducer;

    @Override
    protected Hacker merge(Hacker current, Hacker next) {
        current.getProjects().addAll(next.getProjects());

        Map<Integer, Skill> skills = Stream
                .concat(current.getSkills().stream(), next.getSkills().stream())
                .collect(groupingBy(Skill::getId, reducing(null, skillsReducer.reduce())));

        current.setSkills(new ArrayList<>(skills.values()));

        return current;
    }
}
