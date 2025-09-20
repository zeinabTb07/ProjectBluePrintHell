package  shared.api.dto;

import java.awt.*;
import java.util.UUID;

public class WireDTO {
    private UUID id;
    private Color color;
    private UUID sourceId;
    private UUID destinationId;

    public WireDTO(Color color, UUID sourceId, UUID destinationId) {
        this.color = color;
        this.sourceId=sourceId;
        this.destinationId=destinationId;
    }

    // Getters & Setters
    public UUID getId(){ return id; }
    public Color getColor() {
        return color;
    }
    public UUID getSourceId() {
        return sourceId;
    }
    public UUID getDestinationId() {
        return destinationId;
    }

}

