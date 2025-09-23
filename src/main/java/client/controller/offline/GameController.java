package client.controller.offline;

import shared.events.EventHandler;
import shared.model.GameState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import client.Constants;
import shared.model.levels.Level1;


public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private FrameManager frameManager;
    private  GameState gameState;
    private GameLoop gameLoop ;
    public GameController(){
        Constants.initLevels();
        load();
        gameState = new GameState( new Level1());
        frameManager = new FrameManager(gameState);
        gameLoop = new GameLoop(gameState);
        EventHandler eventHandler = new EventHandler(frameManager , gameLoop , gameState);
    }
    private synchronized void save(){

    }
    private synchronized void load(){
    }
}
