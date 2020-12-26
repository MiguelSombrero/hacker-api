package com.hacker.api.domain.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {
    private String name;

    @EqualsAndHashCode.Exclude
    private int knowHowMonths;
}
