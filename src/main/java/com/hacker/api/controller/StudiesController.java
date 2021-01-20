package com.hacker.api.controller;

import com.hacker.api.domain.studies.*;
import com.hacker.api.parsers.SheetParserImpl;
import com.hacker.api.service.StudiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/studies")
public class StudiesController {
    protected static Logger logger = LoggerFactory.getLogger(StudiesController.class);

    @Autowired
    private StudiesService studiesService;

    @GetMapping("/books")
    public List<Rateable> getBooks() throws IOException {
        List<Rateable> result = studiesService.getBooks();
        return result;
    }

    @GetMapping("/books/reviews")
    public List<Review> getBookReviews() throws IOException {
        List<Review> result = studiesService.getBookReviews();
        return result;
    }

    @PostMapping("/books/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookReview(@RequestBody AudioBookReview audioBookReview) throws IOException {
        studiesService.addBookReview(audioBookReview);
    }

    @GetMapping("/courses")
    public List<Rateable> getCourses() throws IOException {
        List<Rateable> result = studiesService.getCourses();
        return result;
    }

    @GetMapping("/courses/reviews")
    public List<Review> getCourseReviews() throws IOException {
        List<Review> result = studiesService.getCourseReviews();
        return result;
    }
}
