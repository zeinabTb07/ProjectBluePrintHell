package client.controller.Network;

import java.io.*;
import java.net.Socket;

public class Client {
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        if(socket == null){
            try {
                socket = new Socket(host, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("Connected to server: " + socket.getRemoteSocketAddress());

                new Thread(new ServerListener(in)).start();
                new Thread(new InputHandler(out)).start();

                return true;
            } catch (IOException e) {
                System.out.println("Connection failed: " + e.getMessage());
                return false;
            }
        } else return true;
    }

    public boolean retryConnect() {
        return connect();
    }

    public void close() {
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {}
    }
}
