package controller;

import events.EventBus;
import events.UIEvents;
import model.GameState;
import model.constants.Constants;
import model.constants.Level1;


public class GameController {
    private GameState gameState;
    private FrameManager frameManager;
    private GameLoop gameLoop ;
    public GameController(){
        gameState = new GameState(new Level1());
        gameLoop = new GameLoop(gameState);
        setupEventListeners();
    }
    private void setupEventListeners() {
        EventBus.subscribe(UIEvents.ChooseLevelEvent.class, e -> gameState.resetLevel(Constants.levels.get(e.n())));
    }
    public void run(){
        frameManager = new FrameManager(gameState);
        EventBus.publish(new UIEvents.OpenMenuEvent());
    }

}
