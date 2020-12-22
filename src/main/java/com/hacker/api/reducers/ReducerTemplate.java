package com.hacker.api.reducers;

import java.util.Optional;
import java.util.function.BinaryOperator;

public abstract class ReducerTemplate<T> implements Reducer<T> {

    public BinaryOperator<T> reduce() {
        BinaryOperator<T> reducer = (first, next) -> Optional.ofNullable(first)
                .map(current -> {
                    merge(current, next);
                    return current;
                })
                .orElse(next);

        return reducer;
    }

    protected abstract void merge(T current, T next);
}
