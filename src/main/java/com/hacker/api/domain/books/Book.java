package com.hacker.api.domain.books;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String name;
    private List<Author> authors = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private double rating;

    @EqualsAndHashCode.Exclude
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
