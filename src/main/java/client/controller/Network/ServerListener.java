package client.controller.Network;


import java.io.BufferedReader;
import java.io.IOException;

public class ServerListener implements Runnable {
    private final BufferedReader in;

    public ServerListener(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[SERVER] " + line);
            }
        } catch (IOException e) {
            System.out.println("Server connection lost.");
        }
    }
}
