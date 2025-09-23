package server;

import server.controller.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server server = new Server(8080);
        new Thread(server).start();
    }
}

