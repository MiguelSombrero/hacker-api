package com.hacker.api.domain.books;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private List<Author> authors = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    private Double rating;

    @EqualsAndHashCode.Exclude
    private List<Review> reviews = new ArrayList<>();

    public void calculateRating() {
        this.rating = this.getReviews().stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(Double.NaN);
    }
}
