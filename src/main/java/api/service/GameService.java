package  api.service;

import  api.dto.StatusDTO;
import  api.enums.AbilityType;

import java.util.UUID;

public class GameService {
    public StatusDTO initializeGame() {
        //TODO
        return null;
    }

    public StatusDTO findPacket(UUID gameId, UUID packetId) {
        //TODO
        return null;
    }

    public StatusDTO findSystem(UUID gameId, UUID systemId) {
        //TODO
        return null;
    }

    public StatusDTO findWire(UUID gameId, UUID wireId) {
        //TODO
        return null;
    }

    public StatusDTO getAllSystems(UUID gameId) {
        //TODO
        return null;
    }

    public StatusDTO getAllPackages(UUID gameId) {
        //TODO
        return null;
    }

    public StatusDTO getAllWires(UUID gameId) {
        //TODO
        return null;
    }

    public StatusDTO getGameInfo(UUID gameId) {
        //TODO
        return null;
    }

    public StatusDTO initializeAbility(AbilityType abilityType, UUID playerId) {
        //TODO
        return null;
    }

    public StatusDTO temporalProgress(boolean forward, long time, UUID GameId) {
        //TODO
        return null;
    }

    public StatusDTO addWiring(UUID sourceSystem, UUID destinationSystem, UUID sourcePortId, UUID destinationPortId, UUID gameId, UUID playerId) {
        //TODO
        return null;
    }

    public StatusDTO moveSystem(UUID System, UUID playerId, UUID gameId) {
        //TODO
        return null;
    }
}
