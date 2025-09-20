package client.view.components;

import client.Constants;
import client.view.GameShape;
import shared.api.enums.PortType;
import shared.model.objects.systems.NetworkSystem;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SystemRenderer {
    public void render(Graphics2D g2d, NetworkSystem system) {
        system.update();
        renderSystemBody(g2d, system);
        renderInductor(g2d, system);
        renderInputPorts(g2d, system);
        renderOutputPorts(g2d, system);
    }

    private void renderSystemBody(Graphics2D g2d, NetworkSystem system) {
        if(system.isOverlap()){
            g2d.setColor(Color.RED);
        } else if(!system.isActive()){
            g2d.setColor(Color.lightGray);
        } else g2d.setColor(Constants.Colors.SYSTEM);
        Shape rectangle = new RoundRectangle2D.Double(system.getPoint().getX(),
                system.getPoint().getY() ,
                Constants.SYSTEMS_WIDTH ,
                Math.max(system.getOutputPorts().size(), system.getInputPorts().size())*Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT,
                8 ,
                8);
        g2d.fill(rectangle);
    }

    private void renderInductor(Graphics2D g2d, NetworkSystem system) {
        if(system.getInductor().checkConnections()){
            g2d.setColor(Constants.Colors.STATUS_CONNECTED);
        } else     g2d.setColor(Constants.Colors.STATUS_DISCONNECTED);
        Point p = system.getPoint();
        Rectangle rectangle = new Rectangle(p.x + 2 ,
                p.y + 2 ,
                Constants.SYSTEMS_WIDTH-4 ,
                Constants.INDUCTOR_HEIGHT);
        g2d.fill(rectangle);

        g2d.setColor(Color.BLACK);

        g2d.setFont(new Font("Press Start 2P", Font.PLAIN, (int)(12*Constants.SCALE)));
        g2d.drawString(system.getClass().getSimpleName(),
                p.x+4,
                p.y+Constants.INDUCTOR_HEIGHT-2);
    }

    private void renderInputPorts(Graphics2D g2d, NetworkSystem system) {
        g2d.setColor(Constants.Colors.INPUT_PORT);
        system.getInputPorts()
                .forEach(port ->{g2d.fill(getPortShape(port.getPortType() , port.getPoint()));}
                );
    }

    private Shape getPortShape(PortType portType  ,Point point){
        switch (portType) {
            case RECTANGLE:
                return GameShape.PORT_SQUARE.getShape(point);
            case TRIANGLE:
                return GameShape.PORT_TRIANGLE.getShape(point);
            case BIT_PACKET:
                return GameShape.BITE.getShape(point);
            default:
                throw new IllegalArgumentException("Incorrect port type");
        }
    }

    private void renderOutputPorts(Graphics2D g2d, NetworkSystem system) {
        g2d.setColor(Constants.Colors.OUTPUT_PORT);
        system.getOutputPorts()
                .forEach(port ->{g2d.fill(getPortShape(port.getPortType() , port.getPoint()));}
                );
    }

}