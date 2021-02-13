package com.hacker.api.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class SheetParserImpl implements SheetParser {
    protected static Logger logger = LoggerFactory.getLogger(SheetParserImpl.class);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/yyyy");
    private static DateTimeFormatter dateWithDayFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");

    public String parseStringValue(List<Object> sheet, Integer index) {
        String value = "";

        try {
            value = getStringValue(sheet, index).trim();
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
        }

        return value;
    }

    public int parseIntegerValue(List<Object> sheet, Integer index) {
        int value = 0;

        try {
            String stringValue = parseStringValue(sheet, index);
            value = Integer.valueOf(stringValue);
        } catch (NumberFormatException e) {
            logger.info("Cannot parse value to integer - Returning 0");
        }

        return value;
    }

    public LocalDate parseDateValue(List<Object> sheet, Integer index) {
        LocalDate value = LocalDate.now();
        String stringValue = "";

        try {
            stringValue = parseStringValue(sheet, index);
            YearMonth ym = YearMonth.parse(stringValue, dateFormatter);
            value = ym.atDay(1);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDate with yearmonth formatter");
            logger.info("Trying to parse date with yearmonthday formatter");

            try {
                value = LocalDate.parse(stringValue, dateWithDayFormatter);

            } catch (DateTimeParseException ex) {
                logger.info("Cannot parse value to LocalDate with yearmonthday formatter - Returning current date");
            }
        }

        return value;
    }

    public LocalDateTime parseDateTimeValue(List<Object> sheet, Integer index) {
        LocalDateTime value = LocalDateTime.now();
        String stringValue = "";

        try {
            stringValue = parseStringValue(sheet, index);
            value = LocalDateTime.parse(stringValue, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDateTime - Returning current datetime");
        }

        return value;
    }

    private String getStringValue(List<Object> sheet, Integer index) {
        String value = "";

        if (sheet.size() >= index) {
            value = (String) sheet.get(index);
        }

        return value;
    }

}
