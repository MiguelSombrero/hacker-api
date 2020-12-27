package com.hacker.api.domain.projects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private String name;
    private String client;
    private String employer;
    private Role role;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate start;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate end;

    @EqualsAndHashCode.Exclude
    private String description;
}
