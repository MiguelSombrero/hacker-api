package com.hacker.api.domain;

import com.hacker.api.domain.books.Book;
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
public class Course implements Comparable<Course> {
    private int id;

    @EqualsAndHashCode.Include
    private String name;

    private int duration;
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
    public int compareTo(Course course) {
        double value = this.rating - course.getRating();

        if (value < 0) {
            return 1;
        } else if (value > 0) {
            return -1;
        }

        return 0;
    }

}
