package com.hacker.api.controller;

import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import com.hacker.api.service.StudiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/studies")
public class StudiesController {

    @Autowired
    private StudiesService studiesService;

    @GetMapping("/books")
    public List<Book> getBooks() throws IOException {
        List<Book> result = studiesService.getBooks();
        return result;
    }

    @GetMapping("/reviews")
    public List<Review> getReviews() throws IOException {
        List<Review> result = studiesService.getReviews();
        return result;
    }
}
