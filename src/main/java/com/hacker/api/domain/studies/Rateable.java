package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Rateable implements Comparable<Rateable> {
    private int id;
    private double rating;
    private List<Review> reviews = new ArrayList<>();

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

    public static List<Rateable> calculateRatingAndReturnSorted(Collection<Rateable> reviewable) {
        List<Rateable> sorted = reviewable.stream()
                .map(Rateable::calculateRatingAndReturn)
                .sorted()
                .collect(Collectors.toList());

        return sorted;
    }

    protected static Rateable calculateRatingAndReturn(Rateable rateable) {
        double value = rateable.reviews.stream()
                .mapToInt(review -> review.getRating())
                .filter(rating -> rating != 0)
                .average()
                .orElse(Double.NaN);

        rateable.setRating((double) Math.round(value * 10) / 10);

        return rateable;
    }
}
