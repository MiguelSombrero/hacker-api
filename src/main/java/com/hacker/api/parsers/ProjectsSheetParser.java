package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Role;
import com.hacker.api.domain.projects.Skill;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectsSheetParser extends SheetParserImpl {

    public Hacker parseProjectHacker(List<Object> row) {
        Hacker hacker = new Hacker();
        hacker.setFirstname(WordUtils.capitalizeFully(getEmployeeFirstName(row)));
        hacker.setLastname(WordUtils.capitalizeFully(getEmployeeLastName(row)));
        hacker.setId(hacker.hashCode());

        return hacker;
    }

    public Project parseProject(List<Object> row) {
        Project project = new Project();
        project.setName(WordUtils.capitalizeFully(getProjectName(row)));
        project.setClient(getClientName(row));
        project.setDescription(getProjectDescription(row));
        project.setEmployer(getEmployerName(row));
        project.setStart(getStartDate(row));
        project.setEnd(getEndDate(row));
        project.setId(project.hashCode());

        return project;
    }

    public Role parseRole(List<Object> row) {

        List<String> tasks = createTasks(row);
        Role role=createRole(row, tasks);
        role.setId(role.hashCode());

        return role;
    }

    private  Role createRole(List<Object> row, List<String> tasks){
        Role role = new Role();
        role.setName(WordUtils.capitalizeFully(getRoleName(row)));
        addTasksToRole(role, tasks);

        return role;
    }

    private void addTasksToRole(Role role, List<String> tasks) {
        List<String> taskList = role.getTasks();
        taskList.addAll(tasks);
    }

    private List<String> createTasks(List<Object> row){
        List<String> tasks = Arrays.asList(getRoleTasks(row).split(",")).stream()
                .map(task -> WordUtils.capitalizeFully(task.trim()))
                .collect(Collectors.toList());
        return tasks;
    }

    public List<Skill> parseSkills(List<Object> row) {
        int knowHow = getProjectDuration(row);
        String[] parts = getSkills(row).split(",");
        List<Skill> skills= mapSkills(parts, knowHow);

        return skills;
    }

    private List<Skill> mapSkills(String[] skillArray, int projectDuration ){
        List<Skill> skills;
        skills = Arrays.stream(skillArray)
                .map(name -> {
                    Skill skill = new Skill();
                    skill.setName(WordUtils.capitalizeFully(name.trim()));
                    skill.setId(skill.hashCode());
                    skill.setKnowHowMonths(projectDuration);

                    return skill;
                })
                .collect(Collectors.toList());
        return skills;
    }

    private int getProjectDuration(List<Object> row) {
        LocalDate projectStartDate =getStartDate(row);
        LocalDate firstDayOfStartDate = projectStartDate.withDayOfMonth(1);
        LocalDate projectEndDate= getEndDate(row);
        LocalDate nextMonthFromEndDate = projectEndDate.plusMonths(1);
        LocalDate firstDayOfNextMonth = nextMonthFromEndDate.withDayOfMonth(1);

        Period period = Period.between(firstDayOfStartDate, firstDayOfNextMonth);

        return period.isNegative() ? 0 : period.getYears() * 12 + period.getMonths();
    }

    private String getEmployeeFirstName(List<Object> row) {
        return parseStringValue(row, 0);
    }

    private String getEmployeeLastName(List<Object> row) {
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
}
