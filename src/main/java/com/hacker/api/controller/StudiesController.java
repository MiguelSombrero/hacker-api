package com.hacker.api.controller;

import com.hacker.api.domain.studies.Rateable;
import com.hacker.api.domain.studies.Review;
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
    public List<Rateable> getBooks() throws IOException {
        List<Rateable> result = studiesService.getBooks();
        return result;
    }

    @GetMapping("/books/reviews")
    public List<Review> getBookReviews() throws IOException {
        List<Review> result = studiesService.getBookReviews();
        return result;
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
