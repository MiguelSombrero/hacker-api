package com.hacker.api.reducers;

import com.hacker.api.domain.books.Book;
import org.springframework.stereotype.Component;

@Component
public class BookReducer extends ReducerTemplate<Book> {
    @Override
    protected Book merge(Book current, Book next) {
        Book book = new Book();
        book.setName(current.getName());
        book.setAuthor(current.getAuthor());
        book.getReviews().addAll(current.getReviews());
        book.getReviews().addAll(next.getReviews());

        return book;
    }
}
