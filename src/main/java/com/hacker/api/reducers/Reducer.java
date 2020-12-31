package com.hacker.api.reducers;

import java.util.Collection;
import java.util.List;

public interface Reducer<T> {
    List<T> reduce(Collection<T> objects);
}
