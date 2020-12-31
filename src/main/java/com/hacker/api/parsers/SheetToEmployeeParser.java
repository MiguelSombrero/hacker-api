package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Role;
import com.hacker.api.domain.projects.Skill;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SheetToEmployeeParser extends SheetParserImpl {

    public Employee parse(List<Object> row) {
        List<Skill> skills = parseSkills(row);
        Project project = parseProject(row);

        Employee employee = new Employee();
        employee.setFirstname(getEmployeeFirstname(row));
        employee.setLastname(getEmployeeLastname(row));
        employee.setId(employee.hashCode());
        employee.getSkills().addAll(skills);
        employee.getProjects().add(project);

        return employee;
    }

    private List<Skill> parseSkills(List<Object> row) {
        int knowHow = getProjectDuration(row);

        String[] parts = getSkills(row).split(",");

        List<Skill> skills = Arrays.stream(parts)
                .map(name -> {
                    Skill skill = new Skill();
                    skill.setName(name.trim());
                    skill.setId(skill.hashCode());
                    skill.setKnowHowMonths(knowHow);

                    return skill;
                })
                .collect(Collectors.toList());

        return skills;
    }

    private Project parseProject(List<Object> row) {
        Project project = new Project();
        project.setName(getProjectName(row));
        project.setClient(getClientName(row));
        project.setDescription(getProjectDescription(row));
        project.setEmployer(getEmployerName(row));
        project.setStart(getStartDate(row));
        project.setEnd(getEndDate(row));
        project.setRole(parseRole(row));
        project.setId(project.hashCode());

        return project;
    }

    private Role parseRole(List<Object> row) {
        List<String> tasks = Arrays.asList(getRoleTasks(row).split(",")).stream()
                .map(task -> task.trim())
                .collect(Collectors.toList());

        Role role = new Role();
        role.setName(getRoleName(row));
        role.getTasks().addAll(tasks);
        role.setId(role.hashCode());

        return role;
    }

    private String getEmployeeFirstname(List<Object> row) {
        return parseStringValue(row, 0);
    }

    private String getEmployeeLastname(List<Object> row) {
        return parseStringValue(row, 1);
    }

    private String getEmployerName(List<Object> row) {
        return parseStringValue(row, 2);
    }

    private String getProjectName(List<Object> row) {
        return parseStringValue(row, 3);
    }

    private String getRoleName(List<Object> row) {
        return parseStringValue(row, 4);
    }

    private String getRoleTasks(List<Object> row) {
        return parseStringValue(row, 5);
    }

    private LocalDate getStartDate(List<Object> row) {
        return parseDateValue(row, 6);
    }

    private LocalDate getEndDate(List<Object> row) {
        return parseDateValue(row, 7);
    }

    private String getClientName(List<Object> row) {
        return parseStringValue(row, 8);
    }

    private String getSkills(List<Object> row) {
        return parseStringValue(row, 9);
    }

    private String getProjectDescription(List<Object> row) {
        return parseStringValue(row, 10);
    }

    private int getProjectDuration(List<Object> row) {
        Period period = Period.between(getStartDate(row), getEndDate(row))
                .plusMonths(1);

        return period.isNegative() ? 0 : period.getMonths();
    }
}
