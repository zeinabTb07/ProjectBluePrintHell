package controller;

import events.EventBus;
import events.GameEvents;
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
        frameManager = new FrameManager(gameState);
        init();
        setupEventListeners();
    }
    private void setupEventListeners() {
        EventBus.subscribe(UIEvents.ChooseLevelEvent.class, e -> { gameState.resetLevel(Constants.levels.get(e.n()));
            init();
            frameManager.getGamePanel().resetInfoBar();
        });
        EventBus.subscribe(GameEvents.StartGameEvent.class, d -> {
            gameLoop.start();
        });

        EventBus.subscribe(GameEvents.PauseGameEvent.class, d -> {
            gameLoop.pauseGame(d.b());
        });
    }
    private void init(){
        gameLoop = new GameLoop(gameState);
    }
    public void run(){
        EventBus.publish(new UIEvents.OpenMenuEvent());
    }

}
