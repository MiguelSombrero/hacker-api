package com.hacker.api.reducers;

import java.util.Collection;

public interface Reducer<T> {
    Collection<T> reduce(Collection<T> objects);
}
