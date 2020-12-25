package com.hacker.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@ActiveProfiles("test")
@SpringBootTest
public class BooksControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getBooks() throws Exception {
        MvcResult result = mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andReturn();
    }
}
