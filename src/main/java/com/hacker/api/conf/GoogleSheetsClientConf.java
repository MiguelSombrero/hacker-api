package com.hacker.api.conf;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.hacker.api.auth.GoogleAuthorizationUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class GoogleSheetsClientConf {

    @Bean
    public Sheets sheetsClient() throws GeneralSecurityException, IOException {
        Credential credential = GoogleAuthorizationUtil.getCredential();

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("hacker-api")
                .build();
    }
}
