package client.controller.mouse;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface MouseMode {
    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void paintLine(Graphics2D g);
}