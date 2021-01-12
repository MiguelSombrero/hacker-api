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
        try {
            String value = getStringValue(sheet, index);
            return value.trim();
        } catch (IndexOutOfBoundsException e) {
            logger.info("Spreadsheet is missing value in index " + index);
        }

        logger.info("Returning empty string");
        return "";
    }

    public int parseIntegerValue(List<Object> sheet, Integer index) {
        try {
            String value = parseStringValue(sheet, index);

            logger.info("Integer for parse is: ");
            logger.info(value);

            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            logger.info("Cannot parse value to integer");
        }

        logger.info("Returning 0");
        return 0;
    }

    public LocalDate parseDateValue(List<Object> sheet, Integer index) {
        String value = "";

        try {
            value = parseStringValue(sheet, index);

            logger.info("Date to parse is: ");
            logger.info(value);

            YearMonth ym = YearMonth.parse(value, dateFormatter);
            return ym.atDay(1);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDate with yearmonth formatter");
            logger.info("Trying to parse date with yearmonthday formatter");

            try {
                return LocalDate.parse(value, dateWithDayFormatter);

            } catch (DateTimeParseException ex) {
                logger.info("Cannot parse value to LocalDate with yearmonthday formatter");
            }
        }

        logger.info("Returning current date");
        return LocalDate.now();
    }

    public LocalDateTime parseDateTimeValue(List<Object> sheet, Integer index) {
        try {
            String value = parseStringValue(sheet, index);

            logger.info("DateTime to parse is: ");
            logger.info(value);

            return LocalDateTime.parse(value, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            logger.info("Cannot parse value to LocalDateTime");
        }

        logger.info("Returning current datetime");
        return LocalDateTime.now();
    }

    private String getStringValue(List<Object> sheet, Integer index) {
        String value = "";

        if (sheet.size() >= index) {
            value = (String) sheet.get(index);
        }

        return value;
    }

}
