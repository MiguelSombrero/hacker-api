package com.hacker.api.reducers;

import java.util.function.BinaryOperator;

public interface Reducer<T> {
    BinaryOperator<T> reduce();
}
