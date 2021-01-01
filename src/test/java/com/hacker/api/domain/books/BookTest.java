package com.hacker.api.domain.books;

import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookTest {

    @Test
    public void ifSameNameBookIsSame() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");

        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        AudioBook book4 = DomainObjectFactory.getAudioBook("Apocalypse Now");

        assertTrue(book1.equals(book2));
        assertTrue(book3.equals(book4));
        assertTrue(book1.hashCode() == book2.hashCode());
        assertTrue(book3.hashCode() == book4.hashCode());
    }

    @Test
    public void ifSameNameAndDifferentPageNumberBookIsSame() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.setPages(203);

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.setPages(345);

        assertTrue(book1.equals(book2));
        assertTrue(book1.hashCode() == book2.hashCode());
    }

    @Test
    public void ifSameNameAndDifferentDurationBookIsSame() {
        AudioBook book1 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book1.setDuration(1);

        AudioBook book2 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        book2.setDuration(2);

        assertTrue(book1.equals(book2));
        assertTrue(book1.hashCode() == book2.hashCode());
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

        assertTrue(book1.equals(book2));
        assertTrue(book3.equals(book4));
        assertTrue(book1.hashCode() == book2.hashCode());
        assertTrue(book3.hashCode() == book4.hashCode());
    }

    @Test
    public void ifDifferentNameBookIsDifferent() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Nov");
        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");
        AudioBook book4 = DomainObjectFactory.getAudioBook("Apocalypse Nov");

        assertTrue(!book1.equals(book2));
        assertTrue(!book3.equals(book4));
        assertTrue(book1.hashCode() != book2.hashCode());
        assertTrue(book3.hashCode() != book4.hashCode());
    }

    @Test
    public void ifDifferentTypeBookIsDifferent() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getEBook("Apocalypse Now");
        AudioBook book3 = DomainObjectFactory.getAudioBook("Apocalypse Now");

        assertTrue(!book1.equals(book2));
        assertTrue(!book2.equals(book3));
        assertTrue(book1.hashCode() != book2.hashCode());
        assertTrue(book2.hashCode() != book3.hashCode());
    }


}
