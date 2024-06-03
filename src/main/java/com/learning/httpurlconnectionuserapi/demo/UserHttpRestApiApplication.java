package com.learning.httpurlconnectionuserapi.demo;


import com.learning.httpurlconnectionuserapi.demo.controllers.UserController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class UserHttpRestApiApplication {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/api/users", new UserController());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);

    }

}
