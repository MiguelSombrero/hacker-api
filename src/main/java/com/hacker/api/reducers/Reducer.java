package com.hacker.api.reducers;

import java.util.Collection;
import java.util.function.BinaryOperator;

public interface Reducer<T> {
    Collection<T> reduce(Collection<T> objects);
    BinaryOperator<T> reduce();
}
