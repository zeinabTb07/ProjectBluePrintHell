package shared.events;

import client.Constants;
import client.controller.FrameManager;
import client.controller.GameLoop;
import client.controller.mouse.GameMouseListener;
import shared.api.service.mapper.EventBusMapper;
import shared.model.GameState;
import shared.model.objects.packets.MessagerPacket;
import shared.model.objects.systems.MergeSystem;
import shared.model.objects.systems.NetworkSystem;
import shared.model.objects.systems.VPNSystem;

import javax.swing.*;
import java.util.HashMap;
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
        frameManager.getShop().setPublisher(this);
        EventBusMapper.addEventBus(gameID);
        subscribeAll();
    }

    public void subscribeAll(){
        EventBus bus = EventBusMapper.getEventBus(gameID);
        bus.subscribe(UIEvents.Replay.class, e -> {
            gameState.reset(Constants.levels.getFirst());
            gameLoop = new GameLoop(gameState);
            frameManager.reset();
        });
        bus.subscribe(UIEvents.OpenShop.class, e -> {
            frameManager.openShop();
            bus.publish(new GameEvents.PauseGameEvent(true));
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
        bus.subscribe(GameEvents.CheckGameEndEvent.class, e-> endOptions(e.b()));
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
                gameMouseListener.switchedPointPicker(type);
            }
        });
        bus.subscribe(GameEvents.SetPowerUpPoint.class , e->{if(e.type()== ShopEvents.PowerUpType.HELPER_POINT) {
            GameMouseListener mod = frameManager.getGamePanel().getGameMouseListener();
            mod.addDraggablePoint(e.point());
        }});
        bus.subscribe(GameEvents.CoinGeneratedEvent.class, e -> {
            gameState.setCoin(gameState.getCoin()+e.n());
           frameManager.reset();});
        bus.subscribe(UIEvents.OpenSetting.class,  e-> {
            frameManager.openSetting();
        });
        bus.subscribe(GameEvents.PacketLostEvent.class, e -> {
            gameState.getPackets().remove(e.packet());
            frameManager.reset();
            if(e.packet() instanceof MessagerPacket){
                MessagerPacket p = (MessagerPacket) e.packet();
                if(p.getParentColossusId()!=null){
                  for(NetworkSystem system : gameState.getNetworkSystems()){
                      if(system instanceof MergeSystem){
                          HashMap<UUID , Integer>  map = ((MergeSystem) system).getColossusPacketLostMap();
                          map.merge(p.getParentColossusId().uuid(), 1, Integer::sum);
                      }
                  }
                }
            }
        });
        bus.subscribe(UIEvents.RepaintGamePanelEvent.class, e -> {
            frameManager.getGamePanel().repaint();
            frameManager.reset();
        });
        bus.subscribe(ShopEvents.PowerUpEvent.class , e->{gameState.setCoin(gameState.getCoin()-e.powerUpType().getPrice());
            frameManager.reset();
        });
    }

    public void endOptions(boolean b){
        EventBus bus = EventBusMapper.getEventBus(gameID);
        String[] options ;
        if(b){
            options = new String[] {"Back to Menu" , "Go To Next Level"};
        } else options = new String[]{"Back to Menu", "Start Over"};
        int choice = JOptionPane.showOptionDialog(
                null,
                b ? "You win!" : "You lost!",
                "Game Finished",
                JOptionPane.DEFAULT_OPTION,
                b ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
             bus.publish(new UIEvents.OpenMenu());
        }
        if(choice == 1){
            if(b){
                int n = gameState.getGameLevel().getNumber();
                n++;
                if(n<Constants.levels.size()){
                    bus.publish(new GameEvents.GoToLevel(n));
                }  else bus.publish(new UIEvents.OpenMenu());
            } else  bus.publish(new UIEvents.Replay());
        }
    }

    @Override
    public void publish(Object event) {
        EventBusMapper.getEventBus(gameID).publish(event);
    }
}
