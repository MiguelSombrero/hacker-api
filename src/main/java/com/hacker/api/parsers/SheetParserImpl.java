package com.hacker.api.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SheetParserImpl implements SheetParser {
    protected static Logger logger = LoggerFactory.getLogger(SheetParserImpl.class);

    public String parseStringValue(List<Object> row, Integer index) {
        try {
            return (String) row.get(index);
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
            logger.info("Row: " + row.toString());
        }

        return "";
    }

    public int parseIntegerValue(List<Object> row, Integer index) {
        try {
            return Integer.valueOf((String) row.get(index));
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
            logger.info("Row: " + row.toString());
        } catch (NumberFormatException e) {
            logger.info("Cannot parse value to integer");
            logger.info("Value: " + row.get(index));
        }

        return 0;
    }

    public LocalDate parseDateValue(List<Object> row, Integer index) {
        try {
            String value = (String) row.get(index);
            return LocalDate.parse(value, getFormatter());
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
            logger.info("Row: " + row.toString());
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDate");
            logger.info("Value: " + row.get(index));
            logger.info("Returning current date");
        }

        return LocalDate.now();
    }

    public LocalDateTime parseDateTimeValue(List<Object> row, Integer index) {
        try {
            String value = (String) row.get(index);
            return LocalDateTime.parse(value, getDateTimeFormatter());
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
            logger.info("Row: " + row.toString());
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDateTime");
            logger.info("Value: " + row.get(index));
            logger.info("Returning current date");
        }

        return LocalDateTime.now();
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern("M/d/yyyy");
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");
    }

}
