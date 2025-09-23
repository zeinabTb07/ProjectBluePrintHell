package server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class MatchHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MatchHandler.class);

    private final Socket player1;
    private final Socket player2;

    public MatchHandler(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        try (
                BufferedReader in1 = new BufferedReader(new InputStreamReader(player1.getInputStream()));
                BufferedReader in2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));
                PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
                PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);
        ) {
            out1.println("Match found! You're Player 1");
            out2.println("Match found! You're Player 2");

            new Thread(() -> relayMessages(in1, out2, "P1")).start();
            relayMessages(in2, out1, "P2");

        } catch (IOException e) {
            logger.error("Match error", e);
        } finally {
            try { player1.close(); } catch (IOException ignored) {}
            try { player2.close(); } catch (IOException ignored) {}
        }
    }

    private void relayMessages(BufferedReader in, PrintWriter out, String prefix) {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                out.println(prefix + ": " + msg);
            }
        } catch (IOException e) {
            logger.warn("{} disconnected", prefix);
        }
    }
}
