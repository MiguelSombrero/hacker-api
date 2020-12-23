package com.hacker.api.reducers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.service.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class EmployeeReducer extends ReducerTemplate<Employee> {
    @Autowired
    private SkillReducer skillReducer;

    @Override
    protected Employee merge(Employee current, Employee next) {
        current.getSkills().addAll(next.getSkills());

        Collection<Skill> skills = current.getSkills().stream()
                .collect(Collectors.groupingBy(Skill::hashCode, Collectors.reducing(null, skillReducer.reduce())))
                .values();

        current.setSkills(skills.stream().collect(Collectors.toList()));

        return current;
    }
}
