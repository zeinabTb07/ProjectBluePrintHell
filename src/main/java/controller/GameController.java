package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;
import model.constants.levels.Level1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.GameStateLoader;
import model.constants.Constants;

import java.io.IOException;


public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameStateLoader gameStateLoader;
    private  FrameManager frameManager;
    private  GameState gameState;
    private GameLoop gameLoop ;
    public GameController(){
        gameStateLoader = new GameStateLoader();
        Constants.initLevels();
        load();
        frameManager = new FrameManager(gameState);
        gameLoop = new GameLoop(gameState);
        setupEventListeners();
    }
    private void setupEventListeners() {
        EventBus.subscribe(UIEvents.Replay.class, e -> {
            gameState = new GameState(Constants.levels.getFirst());
            gameLoop = new GameLoop(gameState);
            frameManager = new FrameManager(gameState);
            save();
        });
        EventBus.subscribe(GameEvents.StartGameEvent.class, d -> {
            if(!gameLoop.isRunning()){
                gameLoop.start();
            }
            gameState.getConnections().forEach(e->{e.setFreeze(true);});
        });
        EventBus.subscribe(GameEvents.GoToLevel.class , e->{
            gameState.goToLevel(Constants.levels.get(e.n()));
            gameLoop = new GameLoop(gameState);
            frameManager = new FrameManager(gameState);
            save();
        });

        EventBus.subscribe(GameEvents.PauseGameEvent.class, d -> {
            gameLoop.pauseGame(d.b());
        });
        EventBus.subscribe(UIEvents.OpenMenu.class, e ->{
            frameManager.goToMenu();
            save();
        });
        EventBus.subscribe(UIEvents.SaveGame.class , e-> {
           save();
        });
        EventBus.subscribe(UIEvents.OpenGame.class, e -> {
            frameManager.goToGame();
        });
    }
    private synchronized void save(){
        try {
            gameStateLoader.saveGameState(gameState);
        } catch (IOException ex) {
            log.error("Saving game failed");
        }
    }
    private synchronized void load(){
        try {
            gameState = gameStateLoader.loadGameState();
            if(gameState.getTimePassed()!=0) {
                gameLoop = new GameLoop(gameState);
                gameLoop.start();
            }
        } catch (Exception e) {
            gameState = new GameState(new Level1());
        }
    }
}
