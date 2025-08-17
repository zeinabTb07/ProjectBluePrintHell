package events;

import java.util.*;
import java.util.function.Consumer;

public class EventBus {
    private static final Map<Class<?>, List<Consumer<Object>>> listeners = Collections.synchronizedMap(new HashMap<>());

    public static <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        synchronized (listeners) {
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add((Consumer<Object>) listener);
        }
    }

    public static <T> void publish(T event) {
        List<Consumer<Object>> eventListeners;
        synchronized (listeners) {
            eventListeners = new ArrayList<>(listeners.getOrDefault(event.getClass(), Collections.emptyList()));
        }
        eventListeners.forEach(listener -> listener.accept(event));
    }
}

