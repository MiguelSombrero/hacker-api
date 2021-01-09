package com.hacker.api.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class GoogleAuthorizationUtil {
    protected static Logger logger = LoggerFactory.getLogger(GoogleAuthorizationUtil.class);

    public static Credential getCredential() {
        try {
            InputStream in = GoogleAuthorizationUtil.class
                    .getResourceAsStream("/google-credentials.json");

            GoogleCredential credential = GoogleCredential.fromStream(in)
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));

            return credential;
        } catch (Exception e) {
            logger.info("Could not parse Google Credentials");
        }

        return new GoogleCredential();
    }
}
