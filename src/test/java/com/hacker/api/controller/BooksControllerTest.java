package com.hacker.api.controller;

import com.hacker.api.domain.books.Book;
import com.hacker.api.service.BooksService;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class BooksControllerTest {

    private Book book1;
    private Book book2;

    @MockBean
    private BooksService booksService;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .apply(springSecurity())
                .build();

        Book book1 = DomainObjectFactory.getPaperBook("Apocalypse Now");
        Book book2 = DomainObjectFactory.getPaperBook("Geenin Itsekkyys");

        this.book1 = book1;
        this.book2 = book2;
    }

    @Test
    public void getBooksWhenEverythingOK() throws Exception {
        Mockito.when(booksService.getBooks()).thenReturn(Arrays.asList(book1, book2));

        MvcResult result = mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(jsonPath("$[0].name", is("Apocalypse Now")))
            .andExpect(jsonPath("$[1].name", is("Geenin Itsekkyys")))
            .andReturn();
    }

    @Test
    public void getBooksWhenPathDoesNotExist() throws Exception {
        Mockito.when(booksService.getBooks()).thenReturn(Arrays.asList(book1, book2));

        MvcResult result = mockMvc.perform(get("/books/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getBooksWhenTimeOutException() throws Exception {
        Mockito.when(booksService.getBooks()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBooksWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(booksService.getBooks()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBooksWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(booksService.getBooks()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getBooksWhenIOException() throws Exception {
        Mockito.when(booksService.getBooks()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
