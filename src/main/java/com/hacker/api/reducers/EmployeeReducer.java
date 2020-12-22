package com.hacker.api.reducers;

import com.hacker.api.domain.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeReducer extends ReducerTemplate<Employee> {
    @Override
    protected void merge(Employee current, Employee next) {
        current.getSkills().addAll(next.getSkills());
    }
}
