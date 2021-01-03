package com.hacker.api.domain.books;

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
public abstract class Book {
    private int id;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private BookType type;

    private String authors;
    private double rating;
    private List<Review> reviews = new ArrayList<>();

    public double calculateRating() {
        double value = this.reviews.stream()
                .mapToInt(review -> review.getRating())
                .filter(rating -> rating != 0)
                .average()
                .orElse(Double.NaN);

        return (double) Math.round(value * 10) / 10;
    }
}
