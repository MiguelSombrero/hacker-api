package com.hacker.api.domain.books;

import com.hacker.api.domain.Hacker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;
    private LocalDateTime created;
    private String review;
    private int rating;
    private Hacker reviewer;
}
