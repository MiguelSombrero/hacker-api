package com.hacker.api.domain.books;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private Author author;

    @EqualsAndHashCode.Exclude
    private List<Review> reviews = new ArrayList<>();
}
