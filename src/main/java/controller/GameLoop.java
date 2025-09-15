package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;

import model.GameState;
import model.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(GameLoop.class);
    private static final int FRAME_RATE = 30;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private double realDelta;
    private double delta;
    private GameState gameState;
    private PacketController packetController;
    private CollisionController collisionController;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        packetController = new PacketController(gameState.getPackets());
        collisionController = new CollisionController(gameState.getPackets() , gameState.getCollisions());

        logger.debug("GameLoop initialized with GameState: {}", gameState);
    }



    @Override
    public void run() {
        logger.info("GameLoop started");
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            delta += deltaTime * FRAME_RATE;
            lastTime = now;
            realDelta+=deltaTime;
            if (!paused && delta >= 1) {
                EventBus.publish(new UIEvents.RepaintGamePanelEvent());
                packetController.updatePackets(realDelta);
                collisionController.checkForCollision();
                collisionController.applyCollisions();
                gameState.timePass(realDelta);
                if(gameState.getTimePassed()>gameState.getGameLevel().getTime()){
                    finishGame();
                    EventBus.publish(new GameEvents.CheckGameEndEvent(checkWinCondition()));
                }
                realDelta = 0 ;
                delta--;
            }
        }
    }

    public void pauseGame(boolean b) {
        paused = b;
        if(!b){
            delta = 0;
            realDelta = 0;
        }
    }

    private boolean checkWinCondition(){
        packetController.timesUp();
        return gameState.getCoin()>=0 && gameState.getPacketLossPercentage() <=50;
    }

    public void finishGame() {
        running = false;
    }
}