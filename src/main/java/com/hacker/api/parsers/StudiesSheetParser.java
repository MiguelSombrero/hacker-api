package com.hacker.api.parsers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudiesSheetParser extends SheetParserImpl {

    protected int parseDuration(String duration) {
        int hours = 0;
        int minutes = 0;

        try {
            String[] parts = duration.split(":");
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse duration"));
            return 0;
        } catch (NumberFormatException e) {
            logger.info(String.format("Could not parse duration"));
            return 0;
        }

        return hours * 60 + minutes;
    }

    protected boolean isOfType(List<Object> studiesSheet, String type) {
        String value = getStudyType(studiesSheet).toLowerCase();

        if (!value.isEmpty() && value.equals(type.toLowerCase())) {
            return true;
        }

        return false;
    }

    public String getStudyType(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 2);
    }

}
