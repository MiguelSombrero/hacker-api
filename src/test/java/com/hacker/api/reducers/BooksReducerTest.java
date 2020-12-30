package com.hacker.api.reducers;

import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.VisualBook;
import com.hacker.api.domain.books.Review;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BooksReducerTest {

    @Autowired
    private BooksReducer booksReducer;

    @Test
    public void testMerge() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        Review review2 = DomainObjectFactory.getReview("Could be worse");
        Review review3 = DomainObjectFactory.getReview("Did not like");

        VisualBook book1 = DomainObjectFactory.getVisualBook("Apocalypse Now");
        book1.getReviews().add(review1);
        book1.getReviews().add(review2);

        VisualBook book2 = DomainObjectFactory.getVisualBook("Apocalypse Now");
        book2.getReviews().add(review3);

        Book book3 = booksReducer.merge(book1, book2);

        assertEquals("Apocalypse Now", book3.getName());
        assertEquals(3, book3.getReviews().size());
    }
}
