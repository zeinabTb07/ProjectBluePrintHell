package shared.api.service.mapper;

import shared.events.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventBusMapper {
    public  static Map<UUID , EventBus> gameEventBus = new HashMap<>();
    public static void addEventBus(UUID id){
        EventBus bus = new EventBus();
        gameEventBus.put(id , bus);
    }
    public static void removeEventBus(UUID id){
        gameEventBus.remove(id);
    }
    public static EventBus getEventBus(UUID id){
        return gameEventBus.get(id);
    }
}
