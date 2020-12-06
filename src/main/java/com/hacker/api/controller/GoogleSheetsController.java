package com.hacker.api.controller;

import com.hacker.api.client.GoogleSheetsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sheets")
public class GoogleSheetsController {
    Logger logger = LoggerFactory.getLogger(GoogleSheetsClient.class);

    @Autowired
    private GoogleSheetsClient client;

    @GetMapping("/{id}")
    public Mono<String> getSheet(@PathVariable String id) {
        Mono<String> result = client.getSheet(id);
        return result;
    }
}
