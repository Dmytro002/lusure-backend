package com.example.lesure.api.seatgreek.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SeatgeekRestApiClient {

    @Value("${seatgeek.client-id}")
    private String clientId;

    private final RestTemplate restTemplate;

    public <T, R> T send(HttpMethod method, String path, R requestObj, Class<T> responseType) {
        String url = urlBuilder(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<R> entity = requestObj != null ? new HttpEntity<>(requestObj, headers) : new HttpEntity<>(headers);

        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    responseType
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Failed to execute request: " + e.getStatusCode() + " " + e.getResponseBodyAsString(), e);
        }
    }

    private String urlBuilder(String path) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.seatgeek.com")
                .path("/2")
                .path(path)
                .queryParam("client_id", clientId);

        return uriBuilder.toUriString();
    }
}
