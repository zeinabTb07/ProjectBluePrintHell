package  api.dto;

import api.enums.PortType;

import java.util.UUID;

public class PortDTO {
    UUID id;
    PortType portType;
    System system;

    public PortDTO(PortType portType, System system) {
        this.portType = portType;
        this.system = system;
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
}
