package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SpreadsheetToEmployeesParser extends SpreadsheetParserTemplate {

    private List<List<Object>> values;

    public List<Employee> parseEmployees() {
        return this.values.stream()
                .map(row -> mapToEmployee(row))
                .collect(Collectors.toList());
    }

    private Employee mapToEmployee(List<Object> row) {
        List<Skill> skills = parseSkills(row);

        Employee employee = new Employee();
        employee.setFirstname(getStringValue(row, 0));
        employee.setLastname(getStringValue(row, 1));
        employee.getSkills().addAll(skills);

        return employee;
    }

    private List<Skill> parseSkills(List<Object> row) {
        LocalDate start = getDateValue(row, 6);
        LocalDate end = getDateValue(row, 7);

        Period period = Period
                .between(start, end)
                .plusMonths(1);

        int knowHow = period.isNegative()
                ? 0
                : period.getMonths();

        String value = getStringValue(row, 9);
        String[] parts = value.split(",");

        List<Skill> skills = Arrays.stream(parts)
                .map(name -> new Skill(name.trim(), knowHow))
                .collect(Collectors.toList());

        return skills;
    }

}
