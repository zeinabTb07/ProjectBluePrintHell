package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.controller.MatchHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final ServerSocket serverSocket;
    private volatile boolean running = true;

    private volatile Socket waitingPlayer = null;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        logger.info("Server listening on port {}", serverSocket.getLocalPort());

        while (running && !serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getRemoteSocketAddress());

                synchronized (this) {
                    if (waitingPlayer == null) {
                        // First player waits
                        waitingPlayer = clientSocket;
                        logger.info("Player {} waiting for another player...", clientSocket.getRemoteSocketAddress());
                    } else {
                        // Second player arrives → start match
                        Socket player1 = waitingPlayer;
                        Socket player2 = clientSocket;
                        waitingPlayer = null;

                        logger.info("Match started between {} and {}",
                                player1.getRemoteSocketAddress(), player2.getRemoteSocketAddress());

                        new Thread(new MatchHandler(player1, player2)).start();
                    }
                }

            } catch (IOException e) {
                if (!running || serverSocket.isClosed()) {
                    break;
                }
                logger.error("Error accepting connection", e);
            }
        }
    }

    public void stop() throws IOException {
        running = false;
        if (!serverSocket.isClosed()) {
            serverSocket.close();
        }
        logger.info("Server stopped");
    }
}
