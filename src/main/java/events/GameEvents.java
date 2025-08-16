package events;

import model.objects.packets.Packet;

import java.awt.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GameEvents {
    private static final Logger log = LoggerFactory.getLogger(GameEvents.class);

    public record StartGameEvent() {
        public StartGameEvent {
            log.info("Game started.");
        }
    }

    public record CollisionDetectedEvent(Point p) {
        public CollisionDetectedEvent {
            log.info("Collision detected at coordinates: ({}, {})", p.x, p.y);
        }
    }

    public record PauseGameEvent() {
        public PauseGameEvent {
            log.info("Game paused.");
        }
    }

    public record PacketLostEvent(Packet packet) {
        public PacketLostEvent {
            log.info("Packet lost: {}", packet);
        }
    }

    public record PacketReachedEnd(Packet packet) {
        public PacketReachedEnd {
            log.info("Packet reached end: {}", packet);
        }
    }

    public record CheckConnectivity(boolean b) {
        public CheckConnectivity {
            log.info("Connectivity check result: {}", b ? "Connected" : "Disconnected");
        }
    }
}