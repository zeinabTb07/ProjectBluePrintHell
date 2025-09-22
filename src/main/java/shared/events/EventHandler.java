package shared.events;

import client.Constants;
import client.controller.FrameManager;
import client.controller.GameLoop;
import client.controller.mouse.GameMouseListener;
import shared.api.service.mapper.EventBusMapper;
import shared.model.GameState;

import java.util.UUID;

public class EventHandler implements  Publisher{
    private UUID gameID ;
    private FrameManager frameManager;
    private GameState gameState;
    private GameLoop gameLoop;
    public EventHandler(FrameManager  frameManager , GameLoop gameLoop , GameState gameState ){
        this.frameManager = frameManager;
        this.gameLoop = gameLoop;
        this.gameState = gameState;
        gameID = gameState.getGameID();
        frameManager.getMenuPanel().setPublisher(this);
        gameState.setPublisher(this);
        EventBusMapper.addEventBus(gameID);
    }

    public void subscribeAll(){
        EventBus bus = EventBusMapper.getEventBus(gameID);
        bus.subscribe(UIEvents.Replay.class, e -> {
            gameState.reset(Constants.levels.getFirst());
            gameLoop = new GameLoop(gameState);
            frameManager.reset();
        });
        bus.subscribe(GameEvents.StartGameEvent.class, d -> {
            if(!gameLoop.isRunning()){
                gameLoop.start();
            }
            gameState.getConnections().forEach(e->{e.setFreeze(true);});
        });
        bus.subscribe(GameEvents.GoToLevel.class , e->{
            gameState.goToLevel(Constants.levels.get(e.n()));
            gameLoop = new GameLoop(gameState);
            frameManager.reset();
        });

        bus.subscribe(GameEvents.PauseGameEvent.class, d -> {
            gameLoop.pauseGame(d.b());
        });
        bus.subscribe(UIEvents.OpenMenu.class, e ->{
            frameManager.goToMenu();
        });

        bus.subscribe(UIEvents.OpenGame.class, e -> {
            frameManager.goToGame();
        });
        bus.subscribe(ShopEvents.PowerUpEvent.class, event -> {
            GameMouseListener gameMouseListener = frameManager.getGamePanel().getGameMouseListener();
            ShopEvents.PowerUpType type = event.powerUpType();
            if (type == ShopEvents.PowerUpType.RELOCATE_SYSTEM) {
                gameMouseListener.switchToRelocateSystem();
            } else if (type == ShopEvents.PowerUpType.HELPER_POINT) {
                gameMouseListener.switchToHelperPoint();
            } else if (type== ShopEvents.PowerUpType.ALIGN_CENTER||type== ShopEvents.PowerUpType.ZERO_ACCELERATION) {
                gameMouseListener.switchedPointPickerNormal(type);
            }
        });
        bus.subscribe(GameEvents.SetPowerUpPoint.class , e->{if(e.type()== ShopEvents.PowerUpType.HELPER_POINT) {
            GameMouseListener mod = frameManager.getGamePanel().getGameMouseListener();
            mod.addDraggablePoint(e.point());
        }});
        bus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            gameState.setCoin(gameState.getCoin()+e.n());
           frameManager.reset();});
        bus.subscribe(GameEvents.PacketLostEvent.class, e -> {
            gameState.getPackets().remove(e.packet());
            frameManager.reset();
        });
        bus.subscribe(UIEvents.RepaintGamePanelEvent.class, e -> frameManager.getGamePanel().repaint());
        bus.subscribe(ShopEvents.PowerUpEvent.class , e->{gameState.setCoin(gameState.getCoin()-e.powerUpType().getPrice());
            frameManager.reset();
        });
    }

    @Override
    public void publish(Object event) {
        EventBusMapper.getEventBus(gameID).publish(event);
    }
}
