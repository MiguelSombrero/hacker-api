package com.hacker.api.controller;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.projects.Skill;
import com.hacker.api.reducers.ReducerTemplate;
import com.hacker.api.service.EmployeesService;
import com.hacker.api.utils.DomainObjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
public class EmployeesControllerTest {
    private Logger logger = LoggerFactory.getLogger(EmployeesControllerTest.class);

    Employee employee1;
    Employee employee2;

    @MockBean
    private EmployeesService employeesService;

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

        Employee employee1 = DomainObjectFactory.getEmployee("Miika", "Somero");
        employee1.getSkills().add(skill1);
        employee1.getSkills().add(skill2);
        employee1.getSkills().add(skill3);

        Employee employee2 = DomainObjectFactory.getEmployee("Jukka", "Kainulainen");
        employee2.getSkills().add(skill4);
        employee2.getSkills().add(skill5);
        employee2.getSkills().add(skill6);

        this.employee1 = employee1;
        this.employee2 = employee2;
    }

    @Test
    public void getEmployees() throws Exception {
        Mockito.when(employeesService.getEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        MvcResult result = mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$[0].firstname", is("Miika")))
                .andExpect(jsonPath("$[1].firstname", is("Jukka")))
                .andReturn();

        logger.info("resultti");
        logger.info(result.getResponse().getContentAsString());

    }

}
