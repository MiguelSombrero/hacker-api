package com.hacker.api.domain.books;

import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookTest {

    @Test
    public void bookNameChangesHashcodeAndEquals() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Not Now");

        assertTrue(!book1.equals(book2));
        assertTrue(book1.hashCode() != book2.hashCode());
    }

    @Test
    public void bookTypeChangesHashcodeAndEquals() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        AudioBook book2 = DomainObjectFactory.getAudioBook("Apocalypse Now");

        assertTrue(!book1.equals(book2));
        assertTrue(book1.hashCode() != book2.hashCode());
    }

    @Test
    public void pageNumberDoesNotAffectHashcodeNorEquals() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.setPages(203);

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.setPages(345);

        assertTrue(book1.equals(book2));
        assertTrue(book1.hashCode() == book2.hashCode());
    }

    @Test
    public void authorsDoesNotAffectHashcodeNorEquals() {
        VisualBook book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book1.setAuthors("Miika Somero");

        VisualBook book2 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        book2.setAuthors("Jukka Rantala");

        assertTrue(book1.equals(book2));
        assertTrue(book1.hashCode() == book2.hashCode());
    }
}
