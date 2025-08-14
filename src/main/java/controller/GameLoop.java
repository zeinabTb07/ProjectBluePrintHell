package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;

public class GameLoop extends Thread {
    private static int FRAME_RATE = 60;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private GameState gameState ;
    private PacketController packetController;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        packetController = new PacketController(gameState.getPackets());
        EventBus.subscribe(GameEvents.StartGameEvent.class , e->{start();});
        EventBus.subscribe(GameEvents.PauseGameEvent.class, e->{this.pauseGame();});
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            double deltaTime = now - lastTime / 1_000_000_000.0;
            delta += deltaTime*FRAME_RATE;
            lastTime = now;
            if (!paused && delta >= 1) {
                packetController.updatePackets(deltaTime);
                EventBus.publish(new UIEvents.RepaintGamePanelEvent());
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