package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Rateable implements Comparable<Rateable> {
    private int id;
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

    @Override
    public int compareTo(Rateable rateable) {
        double value = this.rating - rateable.getRating();

        if (value < 0) {
            return 1;
        } else if (value > 0) {
            return -1;
        }

        return 0;
    }

}
