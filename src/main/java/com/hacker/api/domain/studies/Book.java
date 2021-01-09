package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Book extends Rateable {
    private String authors;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private BookType type;
}
