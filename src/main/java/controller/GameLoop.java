package controller;

import model.GameState;
import model.objects.packets.Packet;

public class GameLoop extends Thread {
    private static int FRAME_RATE = 60;
    private static final double NSPF = 1_000_000_000.0 / FRAME_RATE;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private GameState gameState ;
    private PacketController packetController;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        packetController = new PacketController(gameState.getPackets());
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / NSPF;
            lastTime = now;

            if (!paused && delta >= 1) {
                //Todo update game;
                delta--;
            }

        }
    }
    public void pauseGame(){
        paused=!paused;
    }
    public void stopGame(){
        running = false;
    }
}