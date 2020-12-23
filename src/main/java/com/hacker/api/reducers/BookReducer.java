package com.hacker.api.reducers;

import com.hacker.api.domain.books.Book;
import org.springframework.stereotype.Component;

@Component
public class BookReducer extends ReducerTemplate<Book> {
    @Override
    protected Book merge(Book current, Book next) {
        current.getReviews().addAll(next.getReviews());
        return current;
    }
}
