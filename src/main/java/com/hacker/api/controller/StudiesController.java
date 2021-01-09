package com.hacker.api.controller;

import com.hacker.api.domain.studies.Course;
import com.hacker.api.domain.studies.Book;
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

    @GetMapping("/reviews")
    public List<Review> getReviews() throws IOException {
        List<Review> result = studiesService.getReviews();
        return result;
    }

    @GetMapping("/courses")
    public List<Rateable> getCourses() throws IOException {
        List<Rateable> result = studiesService.getCourses();
        return result;
    }
}
