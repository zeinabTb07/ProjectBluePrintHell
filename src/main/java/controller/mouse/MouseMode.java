package controller.mouse;

import controller.NetworkConnectivityChecker;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface MouseMode {
    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void paintLine(Graphics2D g);
    void setConnectivityChecker(NetworkConnectivityChecker checker);
}