package client.controller;

import shared.events.GameEvents;
import shared.events.Publisher;
import shared.events.UIEvents;
import shared.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLoop extends Thread {
    private static transient final Logger logger = LoggerFactory.getLogger(GameLoop.class);
    private static final int FRAME_RATE = 30;
    private volatile boolean running ;
    private volatile boolean paused;
    private double realDelta;
    private double delta;
    private GameState gameState;
    private PacketController packetController;
    private CollisionController collisionController;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        packetController = new PacketController(gameState);
        collisionController = new CollisionController(gameState.getPackets() , gameState.getCollisions());
        logger.debug("GameLoop initialized with GameState: {}", gameState);
    }



    @Override
    public void run() {
        logger.info("GameLoop started");
        running = true;
        long lastTime = System.nanoTime();
        int frameSave = 0 ;

        while (running) {
            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            delta += deltaTime * FRAME_RATE;
            lastTime = now;
            realDelta+=deltaTime;
            if (!paused && delta >= 1) {
                gameState.getPublisher().publish(new UIEvents.RepaintGamePanelEvent());
                packetController.updatePackets(realDelta);
                collisionController.checkForCollision();
                collisionController.applyCollisions();
                gameState.timePass(realDelta);
                frameSave++;
                if(gameState.getTimePassed()>gameState.getGameLevel().getTime()){
                    finishGame();
                }
                realDelta = 0 ;
                delta--;
            }
        }
    }

    public boolean isRunning(){
        return running;
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
        return gameState.getCoin()>=0 &&  gameState.getPackets().size()/gameState.getInitialPackets().size() <=50;
    }

    public void finishGame() {
        running = false;
        gameState.getPublisher().publish(new GameEvents.CheckGameEndEvent(checkWinCondition()));
    }
}