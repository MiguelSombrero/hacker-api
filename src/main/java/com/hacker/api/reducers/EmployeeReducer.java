package com.hacker.api.reducers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class EmployeeReducer extends ReducerTemplate<Employee> {
    @Autowired
    private SkillReducer reducer;

    @Override
    protected Employee merge(Employee current, Employee next) {
        current.getSkills().addAll(next.getSkills());

        Collection<Skill> skills = reducer.reduce(current.getSkills());

        current.setSkills(skills.stream().collect(Collectors.toList()));

        return current;
    }
}
