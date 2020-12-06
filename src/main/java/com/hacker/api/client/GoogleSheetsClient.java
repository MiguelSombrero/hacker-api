package com.hacker.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GoogleSheetsClient {
    Logger logger = LoggerFactory.getLogger(GoogleSheetsClient.class);

    @Value("${com.learningportal.google.sheets.apikey}")
    private String apiKey;

    @Value("${com.learningportal.google.sheets.baseurl}")
    private String baseUrl;

    private final WebClient client = WebClient.builder()
            .baseUrl(baseUrl)
            .build();

    public Mono<String> getSheet(String id) {
        Mono<String> result = client.get()
                .uri(uriBuilder -> uriBuilder.path("/" + id)
                    .queryParam("key", apiKey)
                    .build())
                .retrieve()
                .bodyToMono(String.class);

        return result;
    }
}
