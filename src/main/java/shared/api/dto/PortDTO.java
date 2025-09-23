package  shared.api.dto;

import shared.api.enums.PortType;

import java.awt.*;
import java.util.UUID;

public class PortDTO {
    UUID id;
    PortType portType;
    System system;
    Point point;

    public PortDTO(PortType portType, System system) {
        this.portType = portType;
        this.system = system;
    }

    public PortDTO(PortType portType , Point point , UUID uuid){
        this.portType = portType;
        this.point = point;
        this.id = uuid;
    }


    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public PortType getPortType() {
        return portType;
    }
    public void setPortType(PortType portType) {
        this.portType = portType;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
