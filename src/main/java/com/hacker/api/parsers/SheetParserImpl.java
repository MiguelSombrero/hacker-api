package com.hacker.api.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public abstract class SheetParserImpl implements SheetParser {
    protected static Logger logger = LoggerFactory.getLogger(SheetParserImpl.class);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");

    public String parseStringValue(List<Object> row, Integer index) {
        try {
            String value = (String) row.get(index);
            return value.trim();
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
            logger.info("Row: " + row.toString());
        }

        return "";
    }

    public int parseIntegerValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            logger.info("Cannot parse value to integer");
            logger.info("Value: " + row.get(index));
        }

        return 0;
    }

    public LocalDate parseDateValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);
            return LocalDate.parse(value, dateFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDate");
            logger.info("Value: " + row.get(index));
            logger.info("Returning current date");
        }

        return LocalDate.now();
    }

    public LocalDateTime parseDateTimeValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);
            return LocalDateTime.parse(value, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDateTime");
            logger.info("Value: " + row.get(index));
            logger.info("Returning current date");
        }

        return LocalDateTime.now();
    }

    public abstract Object parse(List<Object> row);
}
