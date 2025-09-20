package client.view.components;

import client.Constants;
import shared.model.objects.other.Connection;
import shared.utils.math.GeometryUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

public class ConnectionRenderer {
    public void render(Graphics2D g, Connection connection) {
        g.setColor(Constants.Colors.CONNECTION);
        g.setStroke(Constants.LINE_STROKE);
        g.draw(GeometryUtils.getPath2d(connection));
        List<Point2D> hp = connection.getHelperPoints();
        for(Point2D point2D : hp){
            Shape shape = new Ellipse2D.Double(point2D.getX()-8 , point2D.getY()-8 , 8 , 8);
            g.setColor(Constants.Colors.HP_POINT);
            g.fill(shape);
        }
    }

}
