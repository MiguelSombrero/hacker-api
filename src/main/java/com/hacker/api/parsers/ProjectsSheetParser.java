package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Role;
import com.hacker.api.domain.projects.Skill;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectsSheetParser extends SheetParserImpl {

    public Hacker parseProjectHacker(List<Object> projectsSheet) {
        Hacker hacker = new Hacker();
        hacker.setFirstName(WordUtils.capitalizeFully(getEmployeeFirstName(projectsSheet)));
        hacker.setLastName(WordUtils.capitalizeFully(getEmployeeLastName(projectsSheet)));
        hacker.setId(hacker.hashCode());

        return hacker;
    }

    public Project parseProject(List<Object> projectsSheet) {
        Project project = new Project();
        project.setName(WordUtils.capitalizeFully(getProjectName(projectsSheet)));
        project.setClient(getClientName(projectsSheet));
        project.setDescription(getProjectDescription(projectsSheet));
        project.setEmployer(getEmployerName(projectsSheet));
        project.setStart(getStartDate(projectsSheet));
        project.setEnd(getEndDate(projectsSheet));
        project.setId(project.hashCode());

        return project;
    }

    public Role parseRole(List<Object> projectsSheet) {
        List<String> tasks = createTasks(projectsSheet);
        Role role=createRole(projectsSheet, tasks);
        role.setId(role.hashCode());

        return role;
    }

    private  Role createRole(List<Object> projectsSheet, List<String> tasks){
        Role role = new Role();
        role.setName(WordUtils.capitalizeFully(getRoleName(projectsSheet)));
        addTasksToRole(role, tasks);

        return role;
    }

    private void addTasksToRole(Role role, List<String> tasks) {
        List<String> taskList = role.getTasks();
        taskList.addAll(tasks);
    }

    private List<String> createTasks(List<Object> projectsSheet){
        List<String> tasks = Arrays.asList(getRoleTasks(projectsSheet).split(",")).stream()
                .map(task -> WordUtils.capitalizeFully(task.trim()))
                .collect(Collectors.toList());
        return tasks;
    }

    public List<Skill> parseSkills(List<Object> projectsSheet) {
        int knowHow = getProjectDuration(projectsSheet);
        String[] parts = getSkills(projectsSheet).split(",");
        List<Skill> skills= mapSkills(parts, knowHow);

        return skills;
    }

    private List<Skill> mapSkills(String[] skillArray, int projectDuration){
        List<Skill> skills = Arrays.stream(skillArray)
                .filter(name -> !name.isEmpty())
                .map(name -> parseSkill(name, projectDuration))
                .collect(Collectors.toList());
        return skills;
    }

    private Skill parseSkill(String name, int projectDuration){
        Skill skill = new Skill();
        skill.setName(WordUtils.capitalizeFully(name.trim()));
        skill.setId(skill.hashCode());
        skill.setKnowHowMonths(projectDuration);

        return skill;
    }

    private int getProjectDuration(List<Object> projectsSheet) {
        LocalDate projectStartDate =getStartDate(projectsSheet);
        LocalDate firstDayOfStartDate = projectStartDate.withDayOfMonth(1);
        LocalDate projectEndDate= getEndDate(projectsSheet);
        LocalDate nextMonthFromEndDate = projectEndDate.plusMonths(1);
        LocalDate firstDayOfNextMonth = nextMonthFromEndDate.withDayOfMonth(1);

        Period period = Period.between(firstDayOfStartDate, firstDayOfNextMonth);

        return period.isNegative() ? 0 : period.getYears() * 12 + period.getMonths();
    }

    private String getEmployeeFirstName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 0);
    }

    private String getEmployeeLastName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 1);
    }

    private String getEmployerName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 2);
    }

    private String getProjectName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 3);
    }

    private String getRoleName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 4);
    }

    private String getRoleTasks(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 5);
    }

    private LocalDate getStartDate(List<Object> projectsSheet) {
        return parseDateValue(projectsSheet, 6);
    }

    private LocalDate getEndDate(List<Object> projectsSheet) {
        return parseDateValue(projectsSheet, 7);
    }

    private String getClientName(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 8);
    }

    private String getSkills(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 9);
    }

    private String getProjectDescription(List<Object> projectsSheet) {
        return parseStringValue(projectsSheet, 10);
    }
}
