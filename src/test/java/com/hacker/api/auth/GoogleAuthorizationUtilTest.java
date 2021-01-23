package com.hacker.api.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class GoogleAuthorizationUtilTest {

    @Test
    public void getCredentialsDoesNotThrowErrorIfCredentialsIsMissing() {
        assertDoesNotThrow(() -> GoogleAuthorizationUtil.getCredential());
    }

}
