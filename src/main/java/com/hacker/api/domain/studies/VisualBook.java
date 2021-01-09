package com.hacker.api.domain.studies;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true, onlyExplicitlyIncluded = true)
public class VisualBook extends Book {
    private int pages;
}
