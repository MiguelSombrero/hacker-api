package com.hacker.api.utils;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.Author;
import com.hacker.api.domain.books.Book;
import com.hacker.api.domain.books.Review;
import com.hacker.api.domain.projects.Skill;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class GoogleSheetsToEmployeesTransformer {
    private static Logger logger = LoggerFactory.getLogger(GoogleSheetsToEmployeesTransformer.class);

    public static Collection<Employee> transform(List<List<Object>> values) {
        Collection<Employee> employees = values.stream()
                .map(row -> employeeMapper(row))
                .collect(Collectors.toList());

        return employees;
    }

    private static Employee employeeMapper(List<Object> row) {
        List<Skill> skills = parseSkills(row);

        Employee employee = new Employee();
        employee.setFirstname((String) row.get(0));
        employee.setLastname((String) row.get(1));
        employee.getSkills().addAll(skills);

        return employee;
    }

    private static List<Skill> parseSkills(List<Object> row) {
        LocalDate start = parseStartDate(row);
        LocalDate end = parseEndDate(row);

        Integer knowHow = Period.between(start, end).getMonths();

        String value = (String) row.get(9);
        String[] parts = value.split(",");

        List<Skill> skills = Arrays.stream(parts)
                .map(name -> new Skill(name, knowHow))
                .collect(Collectors.toList());

        return skills;
    }

    private static LocalDate parseStartDate(List<Object> row) {
        String start = (String) row.get(6);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("01/" + start, formatter);

        return date;
    }

    private static LocalDate parseEndDate(List<Object> row) {
        String start = (String) row.get(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse("01/" + start, formatter);

        return date;
    }

    private static BinaryOperator<Book> employeeReducer() {
        BinaryOperator<Book> reducer = (a, b) -> Optional.ofNullable(a)
                .map(current -> {
                    current.getReviews().addAll(b.getReviews());
                    return current;
                })
                .orElseGet(() -> new Book(b.getName(), b.getAuthor(), new ArrayList<>(b.getReviews())));

        return reducer;
    }

}
