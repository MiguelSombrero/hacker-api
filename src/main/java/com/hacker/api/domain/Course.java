package com.hacker.api.domain;

import com.hacker.api.domain.books.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {
    private int id;

    @EqualsAndHashCode.Include
    private String name;

    private int duration;
    private double rating;
    private List<Review> reviews = new ArrayList<>();

}
