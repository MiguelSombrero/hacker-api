package com.hacker.api.service;

import com.google.api.services.sheets.v4.Sheets;
import com.hacker.api.domain.books.Book;
import com.hacker.api.utils.GoogleSheetsToBooksTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Collection;

@Service
public class BooksService {
    private Logger logger = LoggerFactory.getLogger(BooksService.class);

    @Value("${google.sheets.books.id}")
    private String id;

    @Autowired
    private Sheets sheetsClient;

    public Collection<Book> getBooks() throws IOException {
        ValueRange response = sheetsClient.spreadsheets().values()
                .get(id, "Sheet1")
                .execute();

        Collection<Book> books = GoogleSheetsToBooksTransformer
                .transform(response.getValues());

        return books;
    }

}
