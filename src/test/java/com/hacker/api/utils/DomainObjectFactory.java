package com.hacker.api.utils;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.BookType;
import com.hacker.api.domain.books.VisualBook;
import com.hacker.api.domain.books.Review;
import com.hacker.api.domain.projects.Skill;

public class DomainObjectFactory {

    public static Employee getEmployee(String firstname, String lastname) {
        Employee employee = new Employee();
        employee.setFirstname(firstname);
        employee.setLastname(lastname);
        employee.setId(employee.hashCode());

        return employee;
    }

    public static Skill getSkill(String name, Integer knowHowMonths) {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setKnowHowMonths(knowHowMonths);
        skill.setId(skill.hashCode());

        return skill;
    }

    public static Review getReview(String text) {
        Review review = new Review();
        review.setReview(text);

        return review;
    }

    public static VisualBook getPaperBook(String name) {
        VisualBook book = new VisualBook();
        book.setName(name);
        book.setType(BookType.PAPER);
        book.setId(book.hashCode());
        book.setPages(200);

        return book;
    }
}
