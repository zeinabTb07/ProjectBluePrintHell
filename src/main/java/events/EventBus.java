package events;

import java.util.*;
import java.util.function.Consumer;

public class EventBus {

    private static final Map<Class<?>, List<Consumer<Object>>> listeners = new HashMap<>();


    public static <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                .add((Consumer<Object>) listener);
    }

    public static <T> void publish(T event) {
        List<Consumer<Object>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            eventListeners.forEach(listener -> listener.accept(event));
        }
    }
}

