package com.hacker.api.domain.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private int id;
    private String firstname;
    private String lastname;
}
