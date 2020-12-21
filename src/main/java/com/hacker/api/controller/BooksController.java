package com.hacker.api.controller;

import com.hacker.api.domain.books.Book;
import com.hacker.api.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService service;

    @GetMapping(path = "/", produces = "application/json")
    @ResponseBody
    public Collection<Book> getBooks() throws IOException {
        Collection<Book> result = service.getBooks();
        return result;
    }
}
