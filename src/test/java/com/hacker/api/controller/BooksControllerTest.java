package com.hacker.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacker.api.domain.studies.AudioBookReview;
import com.hacker.api.domain.studies.Book;
import com.hacker.api.domain.studies.Review;
import com.hacker.api.service.StudiesService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BooksControllerTest {

    private Book book1;
    private Book book2;
    private Review review1;
    private Review review2;

    @MockBean
    private StudiesService studiesService;

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

        Review review1 = DomainObjectFactory.getReview("Tosi Hyvä!");
        review1.setRating(5);

        Review review2 = DomainObjectFactory.getReview("Njaa");
        review2.setRating(2);

        this.book1 = book1;
        this.book2 = book2;
        this.review1 = review1;
        this.review2 = review2;
    }

    @Test
    public void getBooksWhenEverythingOK() throws Exception {
        Mockito.when(studiesService.getBooks()).thenReturn(Arrays.asList(book1, book2));

        MvcResult result = mockMvc.perform(get("/studies/books"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(jsonPath("$[0].name", is("Apocalypse Now")))
            .andExpect(jsonPath("$[1].name", is("Geenin Itsekkyys")))
            .andReturn();
    }

    @Test
    public void getBooksWhenPathDoesNotExist() throws Exception {
        Mockito.when(studiesService.getBooks()).thenReturn(Arrays.asList(book1, book2));

        MvcResult result = mockMvc.perform(get("/studies/books/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getBooksWhenTimeOutException() throws Exception {
        Mockito.when(studiesService.getBooks()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/studies/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBooksWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(studiesService.getBooks()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/studies/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBooksWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(studiesService.getBooks()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/studies/books"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getBooksWhenIOException() throws Exception {
        Mockito.when(studiesService.getBooks()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/studies/books"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenEverythingOK() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenReturn(Arrays.asList(review1, review2));

        MvcResult result = mockMvc.perform(get("/studies/books/reviews"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$[0].review", is("Tosi Hyvä!")))
                .andExpect(jsonPath("$[1].review", is("Njaa")))
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenPathDoesNotExist() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenReturn(Arrays.asList(review1, review2));

        MvcResult result = mockMvc.perform(get("/studies/books/reviews/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenTimeOutException() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/studies/books/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/studies/books/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/studies/books/reviews"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getBookReviewsWhenIOException() throws Exception {
        Mockito.when(studiesService.getBookReviews()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/studies/books/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void postBookReviewWhenEverythingOK() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenAnswer(a -> a.getArguments()[0]);

        String body = objectAsString(this.review1);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                    .contentType("application/json")
                    .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$.review", is("Tosi Hyvä!")))
                .andExpect(jsonPath("$.rating", is(5)))
                .andReturn();
    }

    @Test
    public void postBookReviewWhenBodyIsIncorrect() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenAnswer(a -> a.getArguments()[0]);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                .contentType("application/json")
                .content("{ somefield: \"testi\" }"))
                .andExpect(status().is(400))
                .andReturn();
    }

    @Test
    public void postBookReviewsWhenPathDoesNotExist() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenAnswer(a -> a.getArguments()[0]);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void postBookReviewsWhenTimeOutException() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenThrow(SocketTimeoutException.class);

        String body = objectAsString(this.review1);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                    .contentType("application/json")
                    .content(body))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void postBookReviewsWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenThrow(IndexOutOfBoundsException.class);

        String body = objectAsString(this.review1);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                    .contentType("application/json")
                    .content(body))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void postBookReviewsWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenThrow(IllegalArgumentException.class);

        String body = objectAsString(this.review1);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                    .contentType("application/json")
                    .content(body))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void postBookReviewsWhenIOException() throws Exception {
        Mockito.when(studiesService.addBookReview(any(Review.class))).thenThrow(IOException.class);

        String body = objectAsString(this.review1);

        MvcResult result = mockMvc.perform(post("/studies/books/reviews")
                    .contentType("application/json")
                    .content(body))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    private String objectAsString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
