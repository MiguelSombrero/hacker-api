package com.hacker.api.service;

import com.hacker.api.client.ProjectsSheetClient;
import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Role;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.parsers.ProjectsSheetParser;
import com.hacker.api.reducers.HackersReducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.reducing;

@Service
public class ProjectsService {
    private Logger logger = LoggerFactory.getLogger(ProjectsService.class);

    @Autowired
    private ProjectsSheetClient projectsSheetClient;

    @Autowired
    private HackersReducer hackersReducer;

    @Autowired
    private ProjectsSheetParser projectsSheetParser;

    public List<Hacker> getHackers() throws IOException {
        List<List<Object>> values = projectsSheetClient.getProjects();

        Map<Integer, Hacker> hackers = values.stream()
                .map(row -> parseHackers(row))
                .collect(groupingBy(Hacker::getId, reducing(null, hackersReducer.reduce())));

        return new ArrayList<>(hackers.values());

    }

    private Hacker parseHackers(List<Object> row) {
        List<Skill> skills = projectsSheetParser.parseSkills(row);
        Role role = projectsSheetParser.parseRole(row);

        Project project = projectsSheetParser.parseProject(row);
        project.setRole(role);

        Hacker hacker = projectsSheetParser.parseProjectHacker(row);
        hacker.getSkills().addAll(skills);
        hacker.getProjects().add(project);

        return hacker;
    }

}
