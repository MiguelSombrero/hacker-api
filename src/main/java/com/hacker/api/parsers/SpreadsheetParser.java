package com.hacker.api.parsers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public abstract class SpreadsheetParser<T> {

    private List<List<Object>> values;

    public Collection<T> getAll() {
        return this.values.stream()
                .map(row -> mapToObject(row))
                .collect(Collectors.toList());
    }

    protected String getValue(List<Object> row, Integer index) {
        return (String) row.get(index);
    }

    protected abstract T mapToObject(List<Object> row);
}
