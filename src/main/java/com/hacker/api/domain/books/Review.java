package com.hacker.api.domain.books;

import com.hacker.api.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String review;
    private int rating;
    private Employee reviewer;
}
