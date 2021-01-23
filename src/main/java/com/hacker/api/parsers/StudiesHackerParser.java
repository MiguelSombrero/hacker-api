package com.hacker.api.parsers;

import com.hacker.api.domain.Hacker;
import com.hacker.api.domain.studies.*;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StudiesHackerParser extends SheetParserImpl {

    private static final int first=0;
    private static final int last=1;

    public Hacker parseStudiesHacker(List<Object> studiesSheet) {
        Hacker hacker;
        String firstName = "";
        String lastName = "";

        try {
            firstName = parseNamesFromList(studiesSheet, first);
            lastName = parseNamesFromList(studiesSheet, last);

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.info(String.format("Could not parse names from row %s", studiesSheet));
        }

        hacker=createHackerWithName(firstName, lastName);
        return hacker;
    }

    private String parseNamesFromList(List<Object> studiesSheet, int whichName){
        String email = getEmail(studiesSheet);
        String[] parts = email.split("@");
        String[] names = parts[0].split("\\.");

        String name = WordUtils.capitalizeFully(names[whichName]);

        return name;
    }

    private Hacker createHackerWithName(String firstName, String lastName){
        Hacker hacker = new Hacker();
        hacker.setFirstName(firstName);
        hacker.setLastName(lastName);
        hacker.setId(hacker.hashCode());
        return hacker;
    }

    private String getEmail(List<Object> studiesSheet) {
        return parseStringValue(studiesSheet, 1);
    }
}
