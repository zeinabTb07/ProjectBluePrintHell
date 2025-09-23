package client.controller.Network;

import java.util.UUID;

public class Player {
    private final UUID playerID;
    public Player(){
        playerID = UUID.randomUUID();
    }

    public UUID getPlayerID() {
        return playerID;
    }
}