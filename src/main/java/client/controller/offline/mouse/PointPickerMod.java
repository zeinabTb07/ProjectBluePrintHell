package client.controller.offline.mouse;

import shared.events.GameEvents;
import shared.events.Publisher;
import shared.events.ShopEvents;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PointPickerMod implements MouseMode{
    public ShopEvents.PowerUpType event ;
    private Publisher publisher ;
    public PointPickerMod(Publisher publisher){
        this.publisher = publisher;
    }
    @Override
    public void mousePressed(MouseEvent e) {
       if(event== ShopEvents.PowerUpType.ALIGN_CENTER || event == ShopEvents.PowerUpType.ZERO_ACCELERATION){
           publisher.publish(new GameEvents.SetPowerUpPoint(event , e.getPoint()));
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
