package view;

import controller.GameState;
import controller.Stage1;
import model.G_System;
import model.Packet;
import model.PacketRender;

import java.awt.*;

public class GameStateRender {
    private final SystemRender systemRender = new SystemRender();
    private final CurveRender curveRender = new CurveRender();
    private final PacketRender packetRender = new PacketRender();
    private GameState gameState;

    public GameStateRender( GameState gameState){
      this.gameState = gameState ;

    }

    public void updateGame(Graphics2D g){
        for(G_System system : GameState.getInstance().getSystems()){
            systemRender.paint(g , system);
        }
        for (Packet packet : GameState.getInstance().getPackets()){
            packetRender.paint(g , packet);
        }

    }


}
