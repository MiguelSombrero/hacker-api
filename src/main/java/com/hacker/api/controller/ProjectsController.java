package com.hacker.api.controller;

import com.hacker.api.domain.Hacker;
import com.hacker.api.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsService service;

    @GetMapping("/hackers")
    public List<Hacker> getHackers() throws IOException {
        List<Hacker> result = service.getHackers();
        return result;
    }
}
