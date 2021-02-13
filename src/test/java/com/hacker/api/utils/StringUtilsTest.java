package com.hacker.api.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StringUtilsTest {

    @Test
    public void normalizeWhenContainingÄ() {
        String result = StringUtils.normalize("Äimärautio");
        assertEquals("Aimarautio", result);
    }

    @Test
    public void normalizeWhenContainingÖ() {
        String result = StringUtils.normalize("Mörsky");
        assertEquals("Morsky", result);
    }
}
