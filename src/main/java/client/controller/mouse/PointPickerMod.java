package client.controller.mouse;

import shared.events.EventBus;
import shared.events.GameEvents;
import shared.events.ShopEvents;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PointPickerMod implements MouseMode{
    public ShopEvents.PowerUpType event ;
    @Override
    public void mousePressed(MouseEvent e) {
       if(event== ShopEvents.PowerUpType.ALIGN_CENTER || event == ShopEvents.PowerUpType.ZERO_ACCELERATION){
           EventBus.publish(new GameEvents.SetPowerUpPoint(event , e.getPoint()));
       }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void paintLine(Graphics2D g) {

    }
}
