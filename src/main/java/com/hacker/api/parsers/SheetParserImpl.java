package com.hacker.api.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class SheetParserImpl implements SheetParser {
    protected static Logger logger = LoggerFactory.getLogger(SheetParserImpl.class);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");

    public String parseStringValue(List<Object> row, Integer index) {
        try {
            String value = getStringValue(row, index);
            return value.trim();
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
        }

        logger.info("Returning empty string");
        return "";
    }

    public int parseIntegerValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);

            logger.info("Integer for parse is: ");
            logger.info(value);

            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            logger.info("Cannot parse value to integer");
        }

        logger.info("Returning 0");
        return 0;
    }

    public LocalDate parseDateValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);

            logger.info("Date to parse is: ");
            logger.info(value);

            return LocalDate.parse(value, dateFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDate");
        }

        logger.info("Returning current date");
        return LocalDate.now();
    }

    public LocalDateTime parseDateTimeValue(List<Object> row, Integer index) {
        try {
            String value = parseStringValue(row, index);

            logger.info("DateTime to parse is: ");
            logger.info(value);

            return LocalDateTime.parse(value, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDateTime");
        }

        logger.info("Returning current datetime");
        return LocalDateTime.now();
    }

    private String getStringValue(List<Object> row, Integer index) {
        String value = "";

        if (row.size() >= index) {
            value = (String) row.get(index);
        }

        return value;
    }

}
