package client.controller;

import shared.events.EventBus;
import shared.events.GameEvents;
import shared.events.UIEvents;
import shared.model.GameState;
import shared.model.levels.Level1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import client.Constants;

import java.io.IOException;


public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private  FrameManager frameManager;
    private  GameState gameState;
    private GameLoop gameLoop ;
    public GameController(){
        Constants.initLevels();
        load();
        frameManager = new FrameManager(gameState);
        gameLoop = new GameLoop(gameState);
        setupEventListeners();
    }
    private void setupEventListeners() {
//        EventBus.subscribe(UIEvents.Replay.class, e -> {
//            gameState.reset(Constants.levels.getFirst());
//            gameLoop = new GameLoop(gameState);
//            frameManager.reset();
//            save();
//        });
//        EventBus.subscribe(GameEvents.StartGameEvent.class, d -> {
//            if(!gameLoop.isRunning()){
//                gameLoop.start();
//            }
//            gameState.getConnections().forEach(e->{e.setFreeze(true);});
//        });
//        EventBus.subscribe(GameEvents.GoToLevel.class , e->{
//            gameState.goToLevel(Constants.levels.get(e.n()));
//            gameLoop = new GameLoop(gameState);
//            frameManager.reset();
//            save();
//        });
//
//        EventBus.subscribe(GameEvents.PauseGameEvent.class, d -> {
//            gameLoop.pauseGame(d.b());
//        });
//        EventBus.subscribe(UIEvents.OpenMenu.class, e ->{
//            frameManager.goToMenu();
//            save();
//        });
//        EventBus.subscribe(UIEvents.SaveGame.class , e-> {
//           save();
//        });
//        EventBus.subscribe(UIEvents.OpenGame.class, e -> {
//            frameManager.goToGame();
//        });
    }
    private synchronized void save(){

    }
    private synchronized void load(){
    }
}
