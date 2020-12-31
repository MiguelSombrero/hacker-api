package com.hacker.api.controller;

import com.hacker.api.domain.books.Book;
import com.hacker.api.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @GetMapping("")
    public List<Book> getBooks() throws IOException {
        List<Book> result = service.getBooks();
        return result;
    }
}
