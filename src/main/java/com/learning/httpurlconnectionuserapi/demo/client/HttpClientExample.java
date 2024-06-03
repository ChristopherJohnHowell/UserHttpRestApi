package com.learning.httpurlconnectionuserapi.demo.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpClientExample {

    private static final String BASE_URL = "http://localhost:8080/api/users";

    private final HttpClient httpClient;

    public HttpClientExample() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void getUsers() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println("Response: " + res.body()));
    }

    public void getUserById(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println("Response: " + res.body()));
    }

    public void createUser(String name, String email) throws Exception {
        String json = String.format("{\"name\":\"%s\", \"email\":\"%s\"}", name, email);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println("Response: " + res.body()));
    }

    public void updateUser(Long id, String name, String email) throws Exception {
        String json = String.format("{\"name\":\"%s\", \"email\":\"%s\"}", name, email);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println("Response: " + res.body()));
    }

    public void deleteUser(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + id))
                .DELETE()
                .build();

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println("Response: " + res.statusCode()));
    }

    public static void main(String[] args) throws Exception {
        HttpClientExample client = new HttpClientExample();

        client.createUser("John Doe", "john@example.com");
        client.createUser("Jane Doe", "jane@example.com");
        client.getUsers();
        client.getUserById(1L);
        client.updateUser(1L, "John Smith", "johnsmith@example.com");
        client.deleteUser(2L);
        client.getUsers();
    }

}
