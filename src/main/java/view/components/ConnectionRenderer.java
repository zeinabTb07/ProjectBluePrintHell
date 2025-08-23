package view.components;

import model.constants.Constants;
import model.objects.other.Connection;
import model.objects.packets.Packet;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ConnectionRenderer {
    public void render(Graphics2D g, Connection connection) {
        connection.update();
        g.setColor(Constants.Colors.CONNECTION);
        g.setStroke(Constants.LINE_STROKE);
        g.draw(connection.getShape());
        List<Point2D> hp = connection.getHelperPoints();
        for(Point2D point2D : hp){
            Shape shape = new Ellipse2D.Double(point2D.getX()-8 , point2D.getY()-8 , 8 , 8);
            g.setColor(Constants.Colors.HP_POINT);
            g.fill(shape);
        }
    }
}
