package com.hacker.api.controller;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.service.HackersService;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class HackersControllerTest {
    private Logger logger = LoggerFactory.getLogger(HackersControllerTest.class);

    Hacker hacker1;
    Hacker hacker2;

    @MockBean
    private HackersService hackersService;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
                .apply(springSecurity())
                .build();

        Skill skill1 = DomainObjectFactory.getSkill("Java", 5);
        Skill skill2 = DomainObjectFactory.getSkill("Java", 2);
        Skill skill3 = DomainObjectFactory.getSkill("React", 5);
        Skill skill4 = DomainObjectFactory.getSkill("Java", 11);
        Skill skill5 = DomainObjectFactory.getSkill("XML", 8);
        Skill skill6 = DomainObjectFactory.getSkill("XML", 2);

        Hacker hacker1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        hacker1.getSkills().add(skill1);
        hacker1.getSkills().add(skill2);
        hacker1.getSkills().add(skill3);

        Hacker hacker2 = DomainObjectFactory.getEmployee("Jukka", "Kainulainen");
        hacker2.getSkills().add(skill4);
        hacker2.getSkills().add(skill5);
        hacker2.getSkills().add(skill6);

        this.hacker1 = hacker1;
        this.hacker2 = hacker2;
    }

    @Test
    public void getHackersWhenEverythingOK() throws Exception {
        Mockito.when(hackersService.getHackers()).thenReturn(Arrays.asList(hacker1, hacker2));

        MvcResult result = mockMvc.perform(get("/hackers"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$[0].firstname", is("Miika")))
                .andExpect(jsonPath("$[1].firstname", is("Jukka")))
                .andReturn();

        logger.info("resultti");
        logger.info(result.getResponse().getContentAsString());

    }

    @Test
    public void getHackersWhenPathDoesNotExist() throws Exception {
        Mockito.when(hackersService.getHackers()).thenReturn(Arrays.asList(hacker1, hacker2));

        MvcResult result = mockMvc.perform(get("/hackers/notexists"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getHackersWhenTimeOutException() throws Exception {
        Mockito.when(hackersService.getHackers()).thenThrow(SocketTimeoutException.class);

        MvcResult result = mockMvc.perform(get("/hackers"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getHackersWhenIndexOutOfBoundsException() throws Exception {
        Mockito.when(hackersService.getHackers()).thenThrow(IndexOutOfBoundsException.class);

        MvcResult result = mockMvc.perform(get("/hackers"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    public void getHackersWhenIndexIllegalArgumentException() throws Exception {
        Mockito.when(hackersService.getHackers()).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(get("/hackers"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getHackersWhenIOException() throws Exception {
        Mockito.when(hackersService.getHackers()).thenThrow(IOException.class);

        MvcResult result = mockMvc.perform(get("/hackers"))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

}
