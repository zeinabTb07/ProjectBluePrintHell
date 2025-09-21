package shared.utils;

import client.Constants;
import shared.model.GameState;
import shared.model.objects.GameObject;
import shared.model.objects.other.Connection;
import shared.model.objects.systems.addon.Inductor;
import shared.model.objects.systems.NetworkSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.utils.math.GeometryUtils;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.*;

public class NetworkConnectivityChecker {
    private static final Logger log = LoggerFactory.getLogger(NetworkConnectivityChecker.class);
    private final GameState gameState;
    public NetworkConnectivityChecker(GameState gameState) {
        this.gameState = gameState;
    }
    public boolean check(){
        return !overlap() && isConnected() && checkAllPortConnected();
    }
    private boolean overlap() {
        log.debug("Checking for overlaps...");

        boolean overlap = false;
        ArrayList<NetworkSystem> systems = new ArrayList<>(gameState.getNetworkSystems());
        ArrayList<Connection> connections = new ArrayList<>(gameState.getConnections());

        systems.forEach(system -> system.setOverlap(false));

        for (var connection : connections) {
            for (var system : systems) {
                if (checkOverlap(connection, system)) {
                    log.debug("Overlap detected: Connection={} with System={}", connection.getId(), system.getId());
                    system.setOverlap(true);
                    overlap = true;
                }
            }
        }
        for(int i = 0 ; i < systems.size() ; i++){
            for(int j = i+1 ; j < systems.size() ; j++){
                NetworkSystem sys1 = systems.get(i);
                NetworkSystem sys2 = systems.get(j);
                if (checkOverlap(sys2 , sys1)) {
                    log.debug("Overlap detected: System={} with System={}",sys2.getId() , sys1.getId());
                    sys1.setOverlap(true);
                    sys2.setOverlap(true);
                    overlap = true;
                }
            }
        }

        log.debug("Overlap check finished. Result={}", overlap);
        return overlap;
    }

    private boolean checkOverlap(GameObject obj1, NetworkSystem obj2) {
        if (log.isDebugEnabled()) {
            log.debug("Checking overlap between {} and {}", obj1.getId(), obj2.getId());
        }
        Shape shape1 ;
        if(obj1 instanceof Connection){
            shape1 = GeometryUtils.getPath2d((Connection) obj1);
        } else {
            NetworkSystem system = (NetworkSystem) obj1;
            shape1  =  new Rectangle((int)system.getPoint().getX(),
                    (int) system.getPoint().getY() ,
                    Constants.SYSTEMS_WIDTH ,
                    (int) (Math.max(obj2.getOutputPorts().size(), obj2.getInputPorts().size())
                            *Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT)
            );

        }

        Shape shape2 =  new Rectangle((int)obj2.getPoint().getX(),
                (int) obj2.getPoint().getY() ,
                Constants.SYSTEMS_WIDTH ,
                (int) (Math.max(obj2.getOutputPorts().size(), obj2.getInputPorts().size())
                        *Constants.PORT_GAP+1.5f*Constants.INDUCTOR_HEIGHT)
                );
        if(shape1 instanceof Path2D){
            BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            shape1 = stroke.createStrokedShape(shape1);
        }
        if (shape1 == null || shape2 == null) {
            log.debug("One of the shapes is null: shape1={}, shape2={}", shape1, shape2);
            return false;
        }

        Rectangle2D bounds1 = shape1.getBounds2D();
        Rectangle2D bounds2 = shape2.getBounds2D();
        if (!bounds1.intersects(bounds2)) {
            log.debug("Bounding boxes don't overlap: {} and {}", bounds1, bounds2);
            return false;
        }

        Area area1 = new Area(shape1);
        Area area2 = new Area(shape2);
        area1.intersect(area2);
        boolean result = !area1.isEmpty();
        log.debug("Overlap result between {} and {} => {}", obj1.getId(), obj2.getId(), result);
        return result;
    }


    private boolean isConnected() {
        ArrayList<NetworkSystem> systems = new ArrayList<>( gameState.getNetworkSystems());
        if (systems.isEmpty()) {
            return true;
        }

        Set<NetworkSystem> visited = new HashSet<>();
        Queue<NetworkSystem> queue = new LinkedList<>();

        NetworkSystem start = systems.get(0);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            NetworkSystem current = queue.poll();
            Set<NetworkSystem> neighbors = current.getNeighbors();

            for (NetworkSystem neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == systems.size();
    }

    private boolean checkAllPortConnected(){
        ArrayList<NetworkSystem> systems = new ArrayList<>(gameState.getNetworkSystems());
        for(NetworkSystem system: systems){
            Inductor inductor = system.getInductor();
            if(!inductor.checkConnections()){return false;}
        }
        return true;
    }
}