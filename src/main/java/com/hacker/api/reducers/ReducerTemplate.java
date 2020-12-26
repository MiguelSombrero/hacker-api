package com.hacker.api.reducers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public abstract class ReducerTemplate<T> implements Reducer<T> {
    protected static Logger logger = LoggerFactory.getLogger(ReducerTemplate.class);

    public Collection<T> reduce(Collection<T> objects) {
        return objects.stream()
                .collect(groupingBy(T::hashCode, reducing(null, (first, next) -> Optional.ofNullable(first)
                        .map(current -> merge(current, next))
                        .orElse(next))))
                .values();
    }

    protected abstract T merge(T current, T next);
}
