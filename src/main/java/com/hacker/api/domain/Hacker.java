package com.hacker.api.domain;

import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hacker {
    private int id;
    private String email;
    private String firstName;
    private String lastName;

    @EqualsAndHashCode.Exclude
    private List<Skill> skills = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private List<Project> projects = new ArrayList<>();
}
