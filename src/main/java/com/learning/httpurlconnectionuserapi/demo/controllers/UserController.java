package com.learning.httpurlconnectionuserapi.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.httpurlconnectionuserapi.demo.models.User;
import com.learning.httpurlconnectionuserapi.demo.repository.UserRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class UserController implements HttpHandler {
    private static UserRepository userRepository = new UserRepository();
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";
        int statusCode = 200;

        if ("/api/users".equals(path) && "GET".equalsIgnoreCase(method)) {
            List<User> users = userRepository.findAll();
            response = objectMapper.writeValueAsString(users);
        } else if (path.matches("/api/users/\\d+") && "GET".equalsIgnoreCase(method)) {
            Long id = Long.parseLong(path.split("/")[3]);
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                response = objectMapper.writeValueAsString(user.get());
            } else {
                response = "User not found";
                statusCode = 404;
            }
        } else if ("/api/users".equals(path) && "POST".equalsIgnoreCase(method)) {
            User user = objectMapper.readValue(exchange.getRequestBody(), User.class);
            user = userRepository.save(user);
            response = objectMapper.writeValueAsString(user);
        } else if (path.matches("/api/users/\\d+") && "PUT".equalsIgnoreCase(method)) {
            Long id = Long.parseLong(path.split("/")[3]);
            User userDetails = objectMapper.readValue(exchange.getRequestBody(), User.class);
            userDetails.setId(id);
            User updatedUser = userRepository.update(userDetails);
            if (updatedUser != null) {
                response = objectMapper.writeValueAsString(updatedUser);
            } else {
                response = "User not found";
                statusCode = 404;
            }
        } else if (path.matches("/api/users/\\d+") && "DELETE".equalsIgnoreCase(method)) {
            Long id = Long.parseLong(path.split("/")[3]);
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.delete(user.get());
                response = "User deleted";
                statusCode = 204;
            } else {
                response = "User not found";
                statusCode = 404;
            }
        } else {
            response = "Not found";
            statusCode = 404;
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
