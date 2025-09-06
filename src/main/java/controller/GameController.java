package controller;

import events.EventBus;
import events.GameEvents;
import events.UIEvents;
import model.GameState;
import utils.GameStateLoader;
import model.constants.Constants;


public class GameController {
    private GameState gameState;
    private FrameManager frameManager;
    private GameLoop gameLoop ;
    private GameStateLoader gameStateLoader;
    public GameController(){
//        gameStateLoader = new GameStateLoader();
//        try {
//            gameState = gameStateLoader.loadGameState();
//        } catch (Exception e) {
//            gameState = new GameState(new Level1());
//        }
        gameState = new GameState(Constants.levels.get(0));
        frameManager = new FrameManager(gameState);
        gameStateLoader = new GameStateLoader();
        gameLoop = new GameLoop(gameState);
        setupEventListeners();
    }
    private void setupEventListeners() {
        EventBus.subscribe(UIEvents.ReplayEvent.class, e -> {
            gameState.resetLevel(Constants.levels.get(0));
            gameLoop = new GameLoop(gameState);
            frameManager.getGamePanel().reset();
        });
        EventBus.subscribe(GameEvents.StartGameEvent.class, d -> {
            gameLoop.start();
            gameState.getConnections().stream()
                    .forEach(e->{e.setFreeze(true);});
        });

        EventBus.subscribe(GameEvents.PauseGameEvent.class, d -> {
            gameLoop.pauseGame(d.b());
        });
        EventBus.subscribe(UIEvents.OpenMenuEvent.class, e ->{
            frameManager.goToMenu();
//            try {
//                gameStateLoader.saveGameState(gameState);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

        });
        EventBus.subscribe(UIEvents.OpenGameEvent.class, e -> {
            frameManager.goToGame();
        });
    }
}
