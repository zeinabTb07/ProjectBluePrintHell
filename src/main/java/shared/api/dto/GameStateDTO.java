package shared.api.dto;

import shared.model.GameState;

import java.util.ArrayList;
import java.util.UUID;

public class GameStateDTO {
    private UUID gameID;
    private ArrayList<WireDTO> connections;
    private ArrayList<PacketDTO> packets ;
    private ArrayList<CollisionDTO> collisions ;
    private ArrayList<SystemDTO> networkSystems ;
    public GameStateDTO(UUID gameID){
        this.gameID = gameID;
    }

    public UUID getGameID() {
        return gameID;
    }

    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    public ArrayList<WireDTO> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<WireDTO> connections) {
        this.connections = connections;
    }

    public ArrayList<PacketDTO> getPackets() {
        return packets;
    }

    public void setPackets(ArrayList<PacketDTO> packets) {
        this.packets = packets;
    }

    public ArrayList<CollisionDTO> getCollisions() {
        return collisions;
    }

    public void setCollisions(ArrayList<CollisionDTO> collisions) {
        this.collisions = collisions;
    }

    public ArrayList<SystemDTO> getNetworkSystems() {
        return networkSystems;
    }

    public void setNetworkSystems(ArrayList<SystemDTO> networkSystems) {
        this.networkSystems = networkSystems;
    }
}
