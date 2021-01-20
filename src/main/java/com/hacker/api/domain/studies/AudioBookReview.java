package com.hacker.api.domain.studies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudioBookReview {
    private String email;
    private String bookName;
    private int bookDuration;
    private String bookAuthors;
    private String review;
    private int rating;
}
