package view.components;

import model.constants.Constants;
import model.objects.packets.Packet;

import java.awt.*;

public class PacketRenderer {
    public void render(Graphics2D g2d , Packet packet){
        packet.update();
        g2d.setColor(Constants.Colors.PACKET);
        g2d.setStroke(Constants.LINE_STROKE);
        g2d.draw(packet.getShape());
    }
}
