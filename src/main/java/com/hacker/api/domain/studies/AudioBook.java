package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true, onlyExplicitlyIncluded = true)
public class AudioBook extends Book {
    private int duration;
}
