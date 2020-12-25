package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EmployeesParser extends SpreadsheetParser<Employee> {

    public EmployeesParser(List<List<Object>> values) {
        super(values);
    }

    protected Employee mapToObject(List<Object> row) {
        List<Skill> skills = parseSkills(row);

        Employee employee = new Employee();
        employee.setFirstname(getValue(row, 0));
        employee.setLastname(getValue(row, 1));
        employee.getSkills().addAll(skills);

        return employee;
    }

    private List<Skill> parseSkills(List<Object> row) {
        LocalDate start = parseDate(row, 6);
        LocalDate end = parseDate(row, 7);

        Integer knowHow = Period
                .between(start, end)
                .plusMonths(1)
                .getMonths();

        String value = getValue(row, 9);
        String[] parts = value.split(",");

        List<Skill> skills = Arrays.stream(parts)
                .map(name -> new Skill(name.trim(), knowHow))
                .collect(Collectors.toList());

        return skills;
    }

    private LocalDate parseDate(List<Object> row, Integer index) {
        String start = getValue(row, index);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate date = LocalDate.parse(start, formatter);

        return date;
    }
    
}
