package model;

import view.Rectangle;
import view.Triangle;

import java.awt.*;

public class PacketRender {
    public  void paint(Graphics2D g , Packet packet){
        if(packet.getType()==Type.Triangle){
            Triangle triangle = new Triangle(packet.getX() , packet.getY() , 3*packet.getSize() , Color.ORANGE.darker().darker());
            triangle.draw(g);
        } else if(packet.getType()==Type.Rectangle){
            Rectangle rectangle = new Rectangle(packet.getX() , packet.getY() , 8*packet.getSize() , 6*packet.getSize() , Color.green.darker().darker());

            rectangle.draw(g);
        }
    }

}
