package com.hacker.api.reducers;

import com.hacker.api.domain.studies.Rateable;
import org.springframework.stereotype.Component;

@Component
public class RateableReducer extends ReducerTemplate<Rateable> {

    @Override
    protected Rateable merge(Rateable current, Rateable next) {
        current.getReviews().addAll(next.getReviews());
        return current;
    }
}
