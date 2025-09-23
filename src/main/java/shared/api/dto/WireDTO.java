package  shared.api.dto;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

public class WireDTO {
    private UUID id;
    private Color color;
    private UUID sourceId;
    ArrayList<Point2D> points;
    private UUID destinationId;

    public WireDTO(Color color, UUID sourceId, UUID destinationId) {
        this.color = color;
        this.sourceId=sourceId;
        this.destinationId=destinationId;
    }

    public WireDTO(UUID id, UUID sourceId, UUID destinationId , ArrayList<Point2D> points) {
        this.id = id;
        this.sourceId=sourceId;
        this.destinationId=destinationId;
        this.points = points;
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    public ArrayList<Point2D> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point2D> points) {
        this.points = points;
    }

    public void setDestinationId(UUID destinationId) {
        this.destinationId = destinationId;
    }
}

