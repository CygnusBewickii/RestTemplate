package org.example;

import org.example.DTO.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Main {

    private final static String url = "http://94.198.50.185:7081/api/users";
    private static String sessionId;
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        ResponseEntity<User[]> usersResponse = getUsersResponse();
        sessionId = usersResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        String firstPartOfCode = makePostRequest().getBody();
        String secondPartOfCode = makePutRequest().getBody();
        String thirdPartOfCode = makeDeleteRequest().getBody();
        System.out.println(firstPartOfCode + secondPartOfCode + thirdPartOfCode);
    }

    private static ResponseEntity<User[]> getUsersResponse() {
        return restTemplate.getForEntity(url, User[].class);
    }

    private static ResponseEntity<String> makePostRequest() {
        User user = new User(3L, "James", "Brown", (byte) 23);
        HttpEntity<User> http = new HttpEntity<>(user, getHeaders());
        return restTemplate.exchange(url, HttpMethod.POST, http, String.class);
    }

    private static ResponseEntity<String> makePutRequest() {
        User user = new User(3L, "Thomas", "Shelby", (byte)30);
        HttpEntity<User> http = new HttpEntity<>(user, getHeaders());
        return restTemplate.exchange(url, HttpMethod.PUT, http, String.class);
    }

    private static ResponseEntity<String> makeDeleteRequest() {
        String deleteUrl = url + "/3";
        HttpEntity http = new HttpEntity(getHeaders());
        return restTemplate.exchange(deleteUrl, HttpMethod.DELETE, http, String.class);
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);
        return headers;
    }
}