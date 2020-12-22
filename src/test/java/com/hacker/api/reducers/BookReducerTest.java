package com.hacker.api.reducers;

import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookReducerTest {

    @Autowired
    private BookReducer bookReducer;

    @Test
    public void testMerge() {
        Review review1 = new Review();
        review1.setReview("Pretty good book");

        Review review2 = new Review();
        review1.setReview("Could be Worse");

        Review review3 = new Review();
        review1.setReview("Did not like");

        Book book1 = new Book();
        book1.setName("Apocalypse Now");
        book1.getReviews().add(review1);
        book1.getReviews().add(review2);

        Book book2 = new Book();
        book1.setName("Apocalypse Now");
        book2.getReviews().add(review3);

        Book book3 = bookReducer.merge(book1, book2);

        assertEquals("Apocalypse Now", book3.getName());
        assertEquals(3, book3.getReviews().size());
    }
}
