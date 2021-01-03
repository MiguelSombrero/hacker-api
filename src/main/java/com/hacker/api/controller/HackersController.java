package com.hacker.api.controller;

import com.hacker.api.domain.Hacker;
import com.hacker.api.service.HackersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class HackersController {

    @Autowired
    private HackersService service;

    @GetMapping("")
    public List<Hacker> getHackers() throws IOException {
        List<Hacker> result = service.getHackers();
        return result;
    }
}
