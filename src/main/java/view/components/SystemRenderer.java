package view.components;

import model.constants.Constants;
import model.objects.systems.NetworkSystem;

import java.awt.*;
import java.util.Collection;
public class SystemRenderer {
    public void render(Graphics2D g2d, NetworkSystem system) {
        renderSystemBody(g2d, system);
        renderInductor(g2d, system);
        renderInputPorts(g2d, system);
        renderOutputPorts(g2d, system);
    }

    private void renderSystemBody(Graphics2D g2d, NetworkSystem system) {
        g2d.setColor(Constants.Colors.SYSTEM);
        g2d.fill(system.getShape());
    }

    private void renderInductor(Graphics2D g2d, NetworkSystem system) {
        if(system.getInductor().checkConnections()){
            g2d.setColor(Constants.Colors.STATUS_CONNECTED);
        } else     g2d.setColor(Constants.Colors.STATUS_DISCONNECTED);
        g2d.fill(system.getInductor().getShape());

        g2d.setColor(Color.BLACK);
        Point p = system.getPoint();
        g2d.drawString(system.getClass().getSimpleName(),
                p.x+4,
                p.y+Constants.INDUCTOR_HEIGHT-2);
    }

    private void renderInputPorts(Graphics2D g2d, NetworkSystem system) {
        g2d.setColor(Constants.Colors.INPUT_PORT);
        system.getInputPorts().values().stream()  // Stream<ArrayList<InputPort>>
                .flatMap(Collection::stream)          // Stream<InputPort>
                .forEach(port -> g2d.fill(
                        port.getShape())
                );
    }

    private void renderOutputPorts(Graphics2D g2d, NetworkSystem system) {
        g2d.setColor(Constants.Colors.OUTPUT_PORT);
        system.getOutputPorts().values().stream() // Stream<ArrayList<OutputPort>>
                .flatMap(Collection::stream)          // Stream<OutputPort>
                .forEach(port -> g2d.fill(
                        port.getShape())
                );
    }

}