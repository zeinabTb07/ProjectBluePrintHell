package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(GameLoop.class);
    private static final int FRAME_RATE = 30;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private GameState gameState;
    private PacketController packetController;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        packetController = new PacketController(gameState.getPackets());
        EventBus.subscribe(GameEvents.StartGameEvent.class, e -> {
            logger.info("Received StartGameEvent, starting GameLoop");
            start();
        });
        EventBus.subscribe(GameEvents.PauseGameEvent.class, e -> {
            pauseGame();
            logger.info("GameLoop paused: {}", paused);
        });
        logger.debug("GameLoop initialized with GameState: {}", gameState);
    }

    @Override
    public void run() {
        logger.info("GameLoop started");
        long lastTime = System.nanoTime();
        double delta = 0;
        double realDelta = 0 ;

        while (running) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            delta += deltaTime * FRAME_RATE;
            lastTime = now;
            realDelta+=deltaTime;
            if (!paused && delta >= 1) {
                EventBus.publish(new UIEvents.RepaintGamePanelEvent());
                packetController.updatePackets(realDelta);
                realDelta = 0 ;
                delta--;
            }
        }
    }

    public void pauseGame() {
        paused = !paused;
    }

    public void stopGame() {
        running = false;
    }
}