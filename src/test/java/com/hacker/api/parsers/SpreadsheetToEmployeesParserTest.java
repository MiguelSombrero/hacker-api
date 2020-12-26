package com.hacker.api.parsers;

import com.hacker.api.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class SpreadsheetToEmployeesParserTest {

    @Test
    public void parseWhenAllFieldsAreCorrect() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(row);

        SpreadsheetToEmployeesParser parser = new SpreadsheetToEmployeesParser(values);

        List<Employee> employees = parser.parseEmployees();

        Employee employee = employees.get(0);

        assertEquals("Miika", employee.getFirstname());
        assertEquals("Somero", employee.getLastname());
        assertEquals(3, employee.getSkills().size());
        assertEquals(4, employee.getSkills().get(0).getKnowHowMonths());
        assertEquals(4, employee.getSkills().get(1).getKnowHowMonths());
        assertEquals(4, employee.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenStartDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "", "11/1/2020", "Alfame", "Java, Ansible, React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(row);

        SpreadsheetToEmployeesParser parser = new SpreadsheetToEmployeesParser(values);

        List<Employee> employees = parser.parseEmployees();

        Employee employee = employees.get(0);

        assertEquals("Miika", employee.getFirstname());
        assertEquals("Somero", employee.getLastname());
        assertEquals(3, employee.getSkills().size());
        assertEquals(0, employee.getSkills().get(0).getKnowHowMonths());
        assertEquals(0, employee.getSkills().get(1).getKnowHowMonths());
        assertEquals(0, employee.getSkills().get(2).getKnowHowMonths());
    }

    @Test
    public void parseWhenEndDateIsMissing() {
        List<Object> row = Stream.of("Miika", "Somero", "Alfame", "Verkkokauppa", "Sovelluskehittäjä", "Toteutus", "8/1/2020", "", "Alfame", "Java,Ansible,React", "Verkkokaupan toteutus")
                .collect(Collectors.toList());

        List<List<Object>> values = Arrays.asList(row);

        SpreadsheetToEmployeesParser parser = new SpreadsheetToEmployeesParser(values);

        List<Employee> employees = parser.parseEmployees();

        Employee employee = employees.get(0);

        int knowHow = Period
                .between(LocalDate.of(2020, 8, 1), LocalDate.now())
                .plusMonths(1)
                .getMonths();

        assertEquals("Miika", employee.getFirstname());
        assertEquals("Somero", employee.getLastname());
        assertEquals(3, employee.getSkills().size());
        assertEquals(knowHow, employee.getSkills().get(0).getKnowHowMonths());
        assertEquals(knowHow, employee.getSkills().get(1).getKnowHowMonths());
        assertEquals(knowHow, employee.getSkills().get(2).getKnowHowMonths());
    }
}
