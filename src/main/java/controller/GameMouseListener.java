package controller;

import model.*;
import view.CurveRender;
import view.GameStateRender;
import view.InfoBar;

import java.awt.event.MouseAdapter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;


public class GameMouseListener extends MouseAdapter {
    private Point dragStart;
    private OutputPort source ;
    private InputPort target ;
    private Line currentLine;
    private final JComponent drawingSurface;
    private final CurveRender curveRender ;

    public GameMouseListener(JComponent drawingSurface) {
        this.drawingSurface = drawingSurface;
        this.curveRender = new CurveRender();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            for(OutputPort port : GameState.getInstance().getOutputPorts() ){
                if(port.getShape().contains(e.getPoint()) && !port.isConnected()){
                    dragStart = new Point(port.getX()+port.getParentSystem().getX()
                            , port.getY()+port.getParentSystem().getY());
                    source = port ;
                    break;
                }

            }

            if (dragStart != null) {
                currentLine = new Line(dragStart, e.getPoint());
            }


        } else if (SwingUtilities.isRightMouseButton(e)) {
            // Use Iterator to safely remove during iteration
            Iterator<Connection> iterator = GameState.getInstance().getConnections().iterator();
            while (iterator.hasNext()) {
                Connection connection = iterator.next();
                if (connection.getSource().getShape().contains(e.getPoint()) ||
                        connection.getTarget().getShape().contains(e.getPoint())) {
                    InfoBar.removeWire(connection.getLength()/2);
                    // TODo: why the hell this loop called twice
                    System.out.println("disconnect " + connection.getLength());
                    iterator.remove(); // Safe removal
                    connection.disconnect(); // Optional: Ensure ports are disconnected
                    GameState.getInstance().removeConnetion(connection);
                }
            }
        }
        
        drawingSurface.repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentLine != null) {
            currentLine.setEnd(e.getPoint());
            drawingSurface.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentLine != null) {
            Point dragEnd = e.getPoint();
            boolean draw = false ;
            for(InputPort port : GameState.getInstance().getInputPorts()){
                if(port.getShape().contains(dragEnd) && source.getParentSystem()!=port.getParentSystem()  && !port.isConnected()){
                    draw = true;
                    dragEnd = new Point(port.getX()+port.getParentSystem().getX()
                            , port.getY()+port.getParentSystem().getY());
                    target = port;
                    Connection connection = new Connection(target , source);
                    GameState.getInstance().addConnetion(connection);
                    InfoBar.addWire(connection.getLength());
                    System.out.println("connect " + connection.getLength());
                    break;
                }
            }
            if (dragEnd != null && !dragEnd.equals(dragStart) && draw) {
                GameState.getInstance().addConnetion(new Connection(target , source));
            }
            currentLine = null;
            dragStart = null;
            source = null;
            target = null ;
            drawingSurface.repaint();
        }
    }

    public void paintConnections(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLUE);

        for(Connection connection : GameState.getInstance().getConnections()){
            curveRender.paint(g2 , connection.getCurve());
        }
        if (currentLine != null) {
            g2.setColor(Color.BLUE);
            g2.drawLine(currentLine.start.x, currentLine.start.y,
                    currentLine.end.x, currentLine.end.y);
        }
    }

    private static class Line {
        Point start, end;
        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public void setEnd(Point end) {
            this.end = end;
        }
    }
}