package com.hacker.api.parsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SpreadsheetParser {
    String parseStringValue(List<Object> row, Integer index);
    int parseIntegerValue(List<Object> row, Integer index);
    LocalDate parseDateValue(List<Object> row, Integer index);
    LocalDateTime parseDateTimeValue(List<Object> row, Integer index);
}
