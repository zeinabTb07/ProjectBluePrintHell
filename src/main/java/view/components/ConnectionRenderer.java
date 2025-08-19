package view.components;

import model.constants.Constants;
import model.objects.other.Connection;

import java.awt.*;

public class ConnectionRenderer {
    public void render(Graphics2D g, Connection connection) {
        connection.update();
        g.setColor(Constants.Colors.CONNECTION);
        g.setStroke(Constants.LINE_STROKE);
        g.draw(connection.getShape());
    }
}
