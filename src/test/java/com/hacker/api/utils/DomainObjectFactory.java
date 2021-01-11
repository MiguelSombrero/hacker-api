package com.hacker.api.utils;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.AudioBook;
import com.hacker.api.domain.studies.BookType;
import com.hacker.api.domain.studies.VisualBook;
import com.hacker.api.domain.studies.Review;
import com.hacker.api.domain.projects.Project;
import com.hacker.api.domain.projects.Skill;

public class DomainObjectFactory {

    public static Hacker getEmployee(String firstname, String lastname) {
        Hacker hacker = new Hacker();
        hacker.setFirstName(firstname);
        hacker.setLastName(lastname);
        hacker.setId(hacker.hashCode());

        return hacker;
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
