package com.hacker.api.domain.studies;

import com.hacker.api.domain.BaseDomainTest;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RateableTest extends BaseDomainTest {

    Review review1, review2, review3, review4, review5;

    @Test
    public void calculatesRatingRightWhenResultIsInteger() {
        setReviewRatings(3,5,5,5,2);

        VisualBook book = getDefaultBook();
        addReviews(book);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(4.0, book.getRating(), 0.01);
    }

    @Test
    public void calculatesRatingRightWhenContainsZeros() {
        setReviewRatings(3,4,0,0,2);

        VisualBook book = getDefaultBook();
        addReviews(book);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(3.0, book.getRating(), 0.01);
    }

    @Test
    public void calculatesRatingRightWhenFractions() {
        setReviewRatings(4,5);

        VisualBook book = getDefaultBook();
        addReviews(book);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(4.5, book.getRating(), 0.01);
    }

    @Test
    public void roundsRatingRightWhenIrrationalNumber() {
        setReviewRatings(4,3,3);

        VisualBook book = getDefaultBook();
        addReviews(book);

        Rateable.calculateRatingAndReturn(book);

        assertEquals(3.3, book.getRating(), 0.01);
    }

    private void setReviewRatings(int rating1, int rating2, int rating3){
        setReviewRatings(rating1, rating2, rating3, 0, 0);
    }

    private void setReviewRatings(int rating1, int rating2){
        setReviewRatings(rating1, rating2, 0, 0, 0);
    }

    private void setReviewRatings(int rating1, int rating2, int rating3, int rating4, int rating5){
        review1 = DomainObjectFactory.getReview("Pretty good book");
        review1.setRating(rating1);

        review2 = DomainObjectFactory.getReview("Could be worse");
        review2.setRating(rating2);

        review3 = DomainObjectFactory.getReview("Seen better");
        review3.setRating(rating3);

        review4 = DomainObjectFactory.getReview("Not my book");
        review4.setRating(rating4);

        review5 = DomainObjectFactory.getReview("OK");
        review5.setRating(rating5);

    }

    private void addReviews(Book book){
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review1);
        reviewList.add(review2);
        reviewList.add(review3);
        reviewList.add(review4);
        reviewList.add(review5);
        book.setReviews(reviewList);
    }
    private VisualBook getDefaultBook(){
        VisualBook book = DomainObjectFactory.getPaperBook("Apocalypse Now");
        return book;
    }
}
