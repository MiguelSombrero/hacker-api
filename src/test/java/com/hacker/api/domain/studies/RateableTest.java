package com.hacker.api.domain.studies;

import com.hacker.api.domain.BaseDomainTest;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RateableTest extends BaseDomainTest {

    @Test
    public void calculatesRatingRightWhenResultIsInteger() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        review1.setRating(3);

        Review review2 = DomainObjectFactory.getReview("Could be worse");
        review2.setRating(5);

        Review review3 = DomainObjectFactory.getReview("Seen better");
        review3.setRating(5);

        Review review4 = DomainObjectFactory.getReview("Not my book");
        review4.setRating(5);

        Review review5 = DomainObjectFactory.getReview("OK");
        review5.setRating(2);

        VisualBook book = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book.getReviews().add(review1);
        book.getReviews().add(review2);
        book.getReviews().add(review3);
        book.getReviews().add(review4);
        book.getReviews().add(review5);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(4.0, book.getRating(), 0.01);
    }

    @Test
    public void calculatesRatingRightWhenContainsZeros() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        review1.setRating(3);

        Review review2 = DomainObjectFactory.getReview("Could be worse");
        review2.setRating(4);

        Review review3 = DomainObjectFactory.getReview("Seen better");
        review3.setRating(0);

        Review review4 = DomainObjectFactory.getReview("Not my book");
        review4.setRating(0);

        Review review5 = DomainObjectFactory.getReview("OK");
        review5.setRating(2);

        VisualBook book = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book.getReviews().add(review1);
        book.getReviews().add(review2);
        book.getReviews().add(review3);
        book.getReviews().add(review4);
        book.getReviews().add(review5);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(3.0, book.getRating(), 0.01);
    }

    @Test
    public void calculatesRatingRightWhenFractions() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        review1.setRating(4);

        Review review2 = DomainObjectFactory.getReview("Could be worse");
        review2.setRating(5);

        VisualBook book = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book.getReviews().add(review1);
        book.getReviews().add(review2);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(4.5, book.getRating(), 0.01);
    }

    @Test
    public void roundsRatingRightWhenIrrationalNumber() {
        Review review1 = DomainObjectFactory.getReview("Pretty good book");
        review1.setRating(4);

        Review review2 = DomainObjectFactory.getReview("Could be worse");
        review2.setRating(3);

        Review review3 = DomainObjectFactory.getReview("OK");
        review3.setRating(3);

        VisualBook book = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book.getReviews().add(review1);
        book.getReviews().add(review2);
        book.getReviews().add(review3);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(3.3, book.getRating(), 0.01);
    }
}
