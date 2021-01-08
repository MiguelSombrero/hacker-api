package com.hacker.api.reducers;

import com.hacker.api.domain.Course;
import com.hacker.api.domain.books.Book;
import org.springframework.stereotype.Component;

@Component
public class CourseReducer extends ReducerTemplate<Course> {

    @Override
    protected Course merge(Course current, Course next) {
        current.getReviews().addAll(next.getReviews());
        return current;
    }
}
