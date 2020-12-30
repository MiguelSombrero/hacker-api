package com.hacker.api.domain.books;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class VisualBook extends Book {
    private int pages;
}
