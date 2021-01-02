package com.hacker.api.utils;

import com.hacker.api.domain.Employee;
import com.hacker.api.domain.books.AudioBook;
import com.hacker.api.domain.books.BookType;
import com.hacker.api.domain.books.VisualBook;
import com.hacker.api.domain.books.Review;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;
import org.apache.commons.text.WordUtils;

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

    public static Project getProject(String name) {
        Project project = new Project();
        project.setName(name);
        project.setId(project.hashCode());

        return project;
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

        return book;
    }

    public static VisualBook getEBook(String name) {
        VisualBook book = new VisualBook();
        book.setName(name);
        book.setType(BookType.EBOOK);
        book.setId(book.hashCode());

        return book;
    }

    public static AudioBook getAudioBook(String name) {
        AudioBook book = new AudioBook();
        book.setName(name);
        book.setType(BookType.AUDIO);
        book.setId(book.hashCode());

        return book;
    }
}
