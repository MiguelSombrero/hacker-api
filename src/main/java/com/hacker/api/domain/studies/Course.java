package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course extends Rateable {
    private int duration;

    @EqualsAndHashCode.Include
    private String name;
}
