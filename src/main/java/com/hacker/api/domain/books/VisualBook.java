package com.hacker.api.domain.books;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisualBook extends Book {
    private int pages;
}
