package com.hacker.api.controller;

import com.hacker.api.domain.studies.Rateable;
import com.hacker.api.domain.studies.Review;
import com.hacker.api.service.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/studies")
public class BooksController {
    protected static Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    private BooksService booksService;

    @GetMapping("/books")
    public List<Rateable> getBooks() throws IOException {
        List<Rateable> result = booksService.getBooks();
        return result;
    }

    @GetMapping("/books/reviews")
    public List<Review> getBookReviews() throws IOException {
        List<Review> result = booksService.getBookReviews();
        return result;
    }

    @PostMapping("/books/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public Review addBookReview(@RequestBody Review review) throws IOException {
        Review result = booksService.addBookReview(review);
        return result;
    }
}
