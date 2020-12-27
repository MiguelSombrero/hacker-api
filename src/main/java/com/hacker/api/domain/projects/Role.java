package com.hacker.api.domain.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private int id;
    private String name;
    private List<String> tasks = new ArrayList<>();
}
