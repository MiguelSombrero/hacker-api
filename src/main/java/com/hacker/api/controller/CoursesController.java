package com.hacker.api.controller;

import com.hacker.api.domain.studies.*;
import com.hacker.api.service.StudiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/studies")
public class CoursesController {
    protected static Logger logger = LoggerFactory.getLogger(CoursesController.class);

    @Autowired
    private StudiesService studiesService;

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
