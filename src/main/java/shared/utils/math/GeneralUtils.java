package shared.utils.math;

import shared.api.dto.*;
import shared.api.enums.SystemType;
import shared.model.GameState;
import shared.model.objects.other.Collision;
import shared.model.objects.other.Connection;
import shared.model.objects.packets.Packet;
import shared.model.objects.systems.*;
import shared.model.objects.systems.addon.Port;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GeneralUtils {
    public static GameStateDTO convertStateToDTO(GameState gameState){
        GameStateDTO dto = new GameStateDTO(gameState.getGameID());
         ArrayList<WireDTO> connections = new ArrayList<>();
         for(Connection connection : gameState.getConnections()){
             connections.add(convertToDTO(connection));
         }
         dto.setConnections(connections);
         ArrayList<PacketDTO> packets = new ArrayList<>();
         gameState.getPackets().forEach(p->{packets.add(convertToDTO(p));});
         dto.setPackets(packets);
         ArrayList<CollisionDTO> collisions = new ArrayList<>() ;
         gameState.getCollisions().forEach(c->collisions.add(convertToDTO(c)));
         dto.setCollisions(collisions);
         ArrayList<SystemDTO> networkSystems = new ArrayList<>();
         gameState.getNetworkSystems().forEach(s->networkSystems.add(convertToDTO(s)));
         dto.setNetworkSystems(networkSystems);
        return dto;
    }
    public static PacketDTO convertToDTO(Packet packet){
        return  new PacketDTO(packet.getType() , packet.getId() , packet.getAbsolutePoint());
    }
    public static PortDTO convertToDTO(Port port){
        return  new PortDTO(port.getPortType() , port.getPoint() ,port.getId());
    }
    public static SystemDTO convertToDTO(NetworkSystem networkSystem){
        ArrayList<PortDTO> inputPorts = new ArrayList<>();
        for(Port port : networkSystem.getInputPorts()){
            inputPorts.add(convertToDTO(port));
        }
        ArrayList<PortDTO> outputPorts = new ArrayList<>();
        for(Port port : networkSystem.getOutputPorts()){
            outputPorts.add(convertToDTO(port));
        }
      return new SystemDTO(getSystemType(networkSystem) , networkSystem.getPoint() , inputPorts , outputPorts , networkSystem.getId());
    }
    public static SystemType getSystemType(NetworkSystem system) {
        switch (system) {
            case RooterSystem router -> {
                return SystemType.ROOTER;
            }
            case LinkSystem link -> {
                return SystemType.NORMAL;
            }
            case VPNSystem firewall -> {
                return SystemType.VPN;
            }
            case DistributeSystem distribute -> {
                return SystemType.DISTRIBUTE;
            }
            case MergeSystem merge -> {
                return SystemType.MERGE;
            }
            case ChaosSystem chaosSystem -> {
                return SystemType.DDOS;
            }
            case SpySystem spy -> {
                return SystemType.SPY;
            }
            case AntiTrojanSystem antiTrojan -> {
                return SystemType.ANTI_TROJAN;
            }
            default -> {
                throw  new RuntimeException("Type not match");
            }
        }
    }
    public static CollisionDTO convertToDTO(Collision collision){
        return new CollisionDTO(collision.getRadius() , collision.getPoint());
    }
    public static WireDTO convertToDTO(Connection connection){
        ArrayList<Point2D> points = new ArrayList<>(connection.getHelperPoint());
        points.addFirst(connection.getSource().getPoint());
        points.addLast(connection.getTarget().getPoint());
        return new WireDTO(connection.getId(), connection.getSource().getId(), connection.getTarget().getId() , points);
    }
}
