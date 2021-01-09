package com.hacker.api.domain.studies;

import com.hacker.api.domain.Hacker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review implements Comparable<Review> {
    private int id;
    private LocalDateTime created;
    private String review;
    private int rating;
    private Hacker reviewer;
    private Book book;

    @Override
    public int compareTo(Review review) {
        double value = this.rating - book.getRating();

        if (this.created.isBefore(review.created)) {
            return 1;
        } else if (this.created.isAfter(review.created)) {
            return -1;
        }

        return 0;
    }
}
