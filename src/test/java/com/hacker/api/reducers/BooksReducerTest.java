package com.hacker.api.reducers;

import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.VisualBook;
import com.hacker.api.domain.books.Review;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BooksReducerTest {
    protected static Logger logger = LoggerFactory.getLogger(BooksReducerTest.class);

    @Autowired
    private BooksReducer booksReducer;

    @Test
    public void testMerge() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        Review review2 = DomainObjectFactory.getReview("Could be worse");
        Review review3 = DomainObjectFactory.getReview("Did not like");

        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.getReviews().add(review1);
        book1.getReviews().add(review2);

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.getReviews().add(review3);

        Book book3 = booksReducer.merge(book1, book2);

        assertEquals("Apocalypse Now", book3.getName());
        assertEquals(3, book3.getReviews().size());
    }

    @Test
    public void testReduceWhenOnlyVisualBooks() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        Review review2 = DomainObjectFactory.getReview("Could be worse");
        Review review3 = DomainObjectFactory.getReview("Did not like");
        Review review4 = DomainObjectFactory.getReview("Will read again");
        Review review5 = DomainObjectFactory.getReview("No, not for me");

        Book book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.getReviews().add(review1);
        book1.getReviews().add(review2);

        Book book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.getReviews().add(review3);

        Book book3 = DomainObjectFactory.getPaperBook("The Selfish Gene");
        book3.getReviews().add(review4);

        Book book4 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book4.getReviews().add(review5);

        Map<Integer, Book> booksMap = Arrays.asList(book1, book2, book3, book4).stream()
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        List<Book> books = new ArrayList<>(booksMap.values());

        Book apocalypse = books.stream()
                .filter(book -> book.getName().equals("Apocalypse Now"))
                .findFirst().get();

        Book selfish = books.stream()
                .filter(book -> book.getName().equals("The Selfish Gene"))
                .findFirst().get();

        assertEquals(2, books.size());
        assertEquals("Apocalypse Now", apocalypse.getName());
        assertEquals("The Selfish Gene", selfish.getName());
        assertEquals(4, apocalypse.getReviews().size());
        assertEquals(1, selfish.getReviews().size());
    }

    @Test
    public void testThatPageNumberDoesNotAffectBookHashCode() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        Review review2 = DomainObjectFactory.getReview("Could be worse");

        VisualBook book1 = DomainObjectFactory.getPaperBook("Clean Code");
        book1.getReviews().add(review1);
        book1.setPages(431);
        book1.setId(book1.hashCode());

        VisualBook book2 = DomainObjectFactory.getPaperBook("Clean Code");
        book2.getReviews().add(review2);
        book2.setPages(434);
        book2.setId(book2.hashCode());

        Map<Integer, Book> booksMap = Arrays.asList(book1, book2).stream()
                .collect(groupingBy(Book::getId, reducing(null, booksReducer.reduce())));

        List<Book> books = new ArrayList<>(booksMap.values());

        assertEquals(1, books.size());
        assertEquals("Clean Code", books.get(0).getName());
        assertEquals(2, books.get(0).getReviews().size());
    }
}
