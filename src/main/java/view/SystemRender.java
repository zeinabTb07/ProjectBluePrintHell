package view;


import model.*;

import java.awt.*;

public class SystemRender {
    public void paint(Graphics2D g, G_System system) {
        drawSystemBackground(g, system);

        drawConnectionStatusBar(g, system);

        for (InputPort port : system.getInputs()) {
            drawPort(g, system, port, false);
        }
        for (OutputPort port : system.getOutput()) {
            drawPort(g, system, port, true);
        }
    }

    private void drawSystemBackground(Graphics2D g, G_System system) {
        g.setColor(Color.DARK_GRAY);
        if(system instanceof ReferenceSystem){
            g.setColor(Color.lightGray);
        }
        g.fillRect(system.getX(), system.getY(), system.getWidth(), system.getHeight());
    }

    private void drawConnectionStatusBar(Graphics2D g, G_System system) {
        Color statusColor = system.getInductor().checkConnections() ? Color.CYAN : Color.LIGHT_GRAY;
        g.setColor(statusColor);
        if(statusColor.equals(Color.lightGray) && system instanceof ReferenceSystem){
            g.setColor(Color.darkGray);
        }
        g.fillRect(system.getX() + 5, system.getY() + 5, system.getWidth() - 10, 15);
    }

    private void drawPort(Graphics2D g, G_System system, Port port, boolean isOutput) {
        int px = system.getX() + port.getX();
        int py = system.getY() + port.getY();
        int border = 3;
        int size = 10 ;
        if(isOutput){
            if(port.getPortType() == Type.Triangle){
                Triangle triangle = new Triangle(px , py ,size + border,  Color.white);
                triangle.draw(g);
            } else if(port.getPortType()== Type.Rectangle){
                Rectangle rectangle = new Rectangle(px , py , size + 10+ 2*border , size + 2*border+ 6 , Color.white);
                rectangle.draw(g);
            }
        }


        if(port.getPortType() == Type.Triangle){
            Triangle triangle = new Triangle(px , py ,size,  Color.green);
            triangle.draw(g);
            port.setShape(triangle);
        } else if(port.getPortType()== Type.Rectangle){
            Rectangle rectangle = new Rectangle(px , py , size + 10 , size + 6 , Color.orange);
            rectangle.draw(g);
            port.setShape(rectangle);
        }


        
    }
}
