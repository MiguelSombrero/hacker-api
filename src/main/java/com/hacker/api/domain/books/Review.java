package com.hacker.api.domain.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hacker.api.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy mm:hh:ss")
    private LocalDateTime created;

    private String review;
    private int rating;
    private Employee reviewer;
}
