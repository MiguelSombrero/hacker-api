package com.hacker.api.domain.books;

import com.hacker.api.domain.BaseDomainTest;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BookTest extends BaseDomainTest {

    @Test
    public void ifSameNameBookIsSame() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");

        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        AudioBook book4 = DomainObjectFactory.getAudioBook("Apocalypse Now");

        isSame(book1, book2);
        isSame(book3, book4);
    }

    @Test
    public void ifSameNameAndDifferentPageNumberBookIsSame() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.setPages(203);

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.setPages(345);

        isSame(book1, book2);
    }

    @Test
    public void ifSameNameAndDifferentDurationBookIsSame() {
        AudioBook book1 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book1.setDuration(1);

        AudioBook book2 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book2.setDuration(2);

        isSame(book1, book2);
    }

    @Test
    public void ifSameNameAndDifferentAuthorBookIsSame() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.setAuthors("Miika Somero");

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.setAuthors("Jukka Rantala");

        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book3.setAuthors("Miika Somero");

        AudioBook book4 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book4.setAuthors("Jukka Rantala");

        isSame(book1, book2);
        isSame(book3, book4);
    }

    @Test
    public void ifDifferentNameBookIsDifferent() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Nov");
        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        AudioBook book4 = DomainObjectFactory.getAudioBook("Apocalypse Nov");

        isDifferent(book1, book2);
        isDifferent(book3, book4);
    }

    @Test
    public void ifDifferentTypeBookIsDifferent() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getEBook("Apocalypse Now");
        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");

        isDifferent(book1, book2);
        isDifferent(book2, book3);
    }

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

        assertEquals(4.0, book.calculateRating(), 0.01);
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

        assertEquals(4.5, book.calculateRating(), 0.01);
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

        assertEquals(3.3, book.calculateRating(), 0.01);
    }
}
