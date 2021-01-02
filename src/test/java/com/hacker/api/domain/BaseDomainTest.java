package com.hacker.api.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseDomainTest {

    protected void isDifferent(Object first, Object second) {
        assertTrue(!first.equals(second));
        assertTrue(first.hashCode() != second.hashCode());
    }

    protected void isSame(Object first, Object second) {
        assertTrue(first.equals(second));
        assertTrue(first.hashCode() == second.hashCode());
    }
}
