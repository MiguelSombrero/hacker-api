package com.hacker.api.parsers;

import java.time.LocalDate;
import java.util.List;

public interface SpreadsheetParser {
    String getStringValue(List<Object> row, Integer index);
    int getIntegerValue(List<Object> row, Integer index);
    LocalDate getDateValue(List<Object> row, Integer index);
}
