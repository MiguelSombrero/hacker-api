package com.hacker.api.service;

import com.hacker.api.client.GoogleSheetsClient;
import com.hacker.api.domain.Hacker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class ProjectsServiceTest {
    protected static Logger logger = LoggerFactory.getLogger(ProjectsServiceTest.class);

    @MockBean
    private GoogleSheetsClient sheetsClient;

    @Autowired
    private ProjectsService projectsService;

    @Test
    public void testGetHackers() throws IOException {
        List<Object> miika1 = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        List<Object> miika2 = Stream.of("Miika", "Somero", "Alfame", "IDM", "Sovelluskehittäjä", "Toteutus", "1/1/2017", "11/30/2017", "Alfame", "Java, Python, XML", "IDM toteutus")
                .collect(Collectors.toList());
        List<Object> miika3 = Stream.of("Miika", "Somero", "Alfame", "Autentikointi", "Määrittelijä", "Määrittely", "5/1/2019", "5/31/2019", "Alfame", "Word, Java, React", "Autentikoinnin toteutus")
                .collect(Collectors.toList());

        List<Object> liisa1 = Stream.of("Liisa", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());
        List<Object> liisa2 = Stream.of("Liisa", "Somero", "Alfame", "IDM", "Sovelluskehittäjä", "Toteutus", "1/1/2017", "11/30/2017", "Alfame", "Java, Python, XML", "IDM toteutus")
                .collect(Collectors.toList());

        List<Object> jukka1 = Stream.of("Jukka", "Jukkanen", "Kela", "Laskentajärjestelmä", "Sovelluskehittäjä, Arkkitehti", "Toteutus, Arkkitehtuuri", "2/1/2018", "12/31/2018", "Kela", "Java, Mule, JSON, Scrum master", "Laskentajärjestelmä")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(miika1, miika2, miika3, liisa1, liisa2, jukka1);

        Mockito.when(sheetsClient.getValuesFromSheet(anyString(), anyString()))
                .thenReturn(values);

        List<Hacker> hackers = projectsService.getHackers();

        Hacker miika = hackers.stream()
                .filter(hacker -> hacker.getFirstName().equals("Miika"))
                .findFirst().get();

        Hacker liisa = hackers.stream()
                .filter(hacker -> hacker.getFirstName().equals("Liisa"))
                .findFirst().get();

        Hacker jukka = hackers.stream()
                .filter(hacker -> hacker.getFirstName().equals("Jukka"))
                .findFirst().get();

        assertEquals(3, hackers.size());

        assertEquals("Miika", miika.getFirstName());
        assertEquals("Somero", miika.getLastName());
        assertEquals(3, miika.getProjects().size());
        assertEquals(6, miika.getSkills().size());
        assertEquals(16, miika.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(5, miika.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(11, miika.getSkills().stream().filter(skill -> skill.getName().equals("Xml")).findFirst().get().getKnowHowMonths());

        assertEquals("Liisa", liisa.getFirstName());
        assertEquals("Somero", liisa.getLastName());
        assertEquals(2, liisa.getProjects().size());
        assertEquals(5, liisa.getSkills().size());
        assertEquals(15, liisa.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(4, liisa.getSkills().stream().filter(skill -> skill.getName().equals("React")).findFirst().get().getKnowHowMonths());
        assertEquals(11, liisa.getSkills().stream().filter(skill -> skill.getName().equals("Xml")).findFirst().get().getKnowHowMonths());

        assertEquals("Jukka", jukka.getFirstName());
        assertEquals("Jukkanen", jukka.getLastName());
        assertEquals(1, jukka.getProjects().size());
        assertEquals(4, jukka.getSkills().size());
        assertEquals(11, jukka.getSkills().stream().filter(skill -> skill.getName().equals("Java")).findFirst().get().getKnowHowMonths());
        assertEquals(11, jukka.getSkills().stream().filter(skill -> skill.getName().equals("Scrum Master")).findFirst().get().getKnowHowMonths());
        assertEquals(11, jukka.getSkills().stream().filter(skill -> skill.getName().equals("Mule")).findFirst().get().getKnowHowMonths());
        assertEquals(11, jukka.getSkills().stream().filter(skill -> skill.getName().equals("Json")).findFirst().get().getKnowHowMonths());
    }
}
