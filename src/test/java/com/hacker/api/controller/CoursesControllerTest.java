package com.hacker.api.controller;

import com.hacker.api.domain.studies.Course;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class CoursesControllerTest {

    private Course course1;
    private Course course2;

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

        Course course1 = DomainObjectFactory.getCourse("Modern React");
        Course course2 = DomainObjectFactory.getCourse("Java Masters");

        Review review1 = DomainObjectFactory.getReview("Tosi Hyvä!");
        review1.setRating(5);

        Review review2 = DomainObjectFactory.getReview("Njaa");
        review2.setRating(2);

        this.review1 = review1;
        this.review2 = review2;
        this.course1 = course1;
        this.course2 = course2;
    }

    @Test
    public void getCoursesWhenEverythingOK() throws Exception {
        Mockito.when(studiesService.getCourses()).thenReturn(Arrays.asList(course1, course2));

        MvcResult result = mockMvc.perform(get("/studies/courses"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/json"))
            .andExpect(jsonPath("$[0].name", is("Modern React")))
            .andExpect(jsonPath("$[1].name", is("Java Masters")))
            .andReturn();
    }

    @Test
    public void getCoursessWhenPathDoesNotExist() throws Exception {
        Mockito.when(studiesService.getCourses()).thenReturn(Arrays.asList(course1, course2));

        MvcResult result = mockMvc.perform(get("/studies/courses/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getCoursesWhenTimeOutException() throws Exception {
        Mockito.when(studiesService.getCourses()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getCoursesWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(studiesService.getCourses()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getCoursessWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(studiesService.getCourses()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getCoursesWhenIOException() throws Exception {
        Mockito.when(studiesService.getCourses()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenEverythingOK() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenReturn(Arrays.asList(review1, review2));

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$[0].review", is("Tosi Hyvä!")))
                .andExpect(jsonPath("$[1].review", is("Njaa")))
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenPathDoesNotExist() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenReturn(Arrays.asList(review1, review2));

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenTimeOutException() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getCourseReviewsWhenIOException() throws Exception {
        Mockito.when(studiesService.getCourseReviews()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/studies/courses/reviews"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
