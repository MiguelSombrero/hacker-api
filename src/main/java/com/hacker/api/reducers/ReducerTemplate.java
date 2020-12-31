package com.hacker.api.reducers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public abstract class ReducerTemplate<T> implements Reducer<T> {
    protected static Logger logger = LoggerFactory.getLogger(ReducerTemplate.class);

    public List<T> reduce(Collection<T> objects) {
        Map<Object, T> reduced = objects.stream()
                .collect(groupingBy(this::getId, reducing(null, (first, next) -> Optional.ofNullable(first)
                        .map(current -> merge(current, next))
                        .orElse(next))));

        return new ArrayList<>(reduced.values());
    }

    protected abstract T merge(T current, T next);

    protected abstract int getId(T object);
}
