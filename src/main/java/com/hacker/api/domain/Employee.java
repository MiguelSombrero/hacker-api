package com.hacker.api.domain;

import com.hacker.api.domain.projects.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String firstname;
    private String lastname;

    private List<Skill> skills = new ArrayList<>();
}
