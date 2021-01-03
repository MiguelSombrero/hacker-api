package com.hacker.api.reducers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BinaryOperator;

public abstract class ReducerTemplate<T> implements Reducer<T> {
    protected static Logger logger = LoggerFactory.getLogger(ReducerTemplate.class);

    public BinaryOperator<T> reduce() {
        BinaryOperator<T> operator = (first, next) -> Optional.ofNullable(first)
                        .map(current -> merge(current, next))
                        .orElse(next);

        return operator;
    }

    protected abstract T merge(T current, T next);
}
