package com.hacker.api.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Profile("!test")
public class GoogleAuthorizationUtil {

    public static Credential getCredential() throws IOException {
        InputStream in = GoogleAuthorizationUtil.class
                .getResourceAsStream("/google-credentials.json");

        GoogleCredential credential = GoogleCredential.fromStream(in)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));

        return credential;
    }
}
