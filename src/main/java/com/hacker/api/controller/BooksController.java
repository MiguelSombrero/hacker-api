package com.hacker.api.controller;

import com.hacker.api.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @GetMapping("/")
    public String getBooks() throws IOException {
        String result = service.getBooks();
        return result;
    }
}
