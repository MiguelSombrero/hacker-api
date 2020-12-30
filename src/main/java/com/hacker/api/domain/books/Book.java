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

    public void calculateRating() {
        double value = this.getReviews().stream()
                .mapToInt(review -> review.getRating())
                .filter(rating -> rating != 0)
                .average()
                .orElse(Double.NaN);

        this.rating = (double) Math.round(value * 10) / 10;
    }
}
