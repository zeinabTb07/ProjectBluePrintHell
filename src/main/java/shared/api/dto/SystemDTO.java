package  shared.api.dto;

import shared.api.enums.SystemType;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public class SystemDTO {
    private UUID id;
    private SystemType systemType;
    private Point position;
    private List<PortDTO> inputPorts;
    private List<PortDTO> outputPorts;

    public SystemDTO(SystemType systemType, int x, int y, List<PortDTO> inputPorts, List<PortDTO> outputPorts) {
        this.systemType = systemType;
        this.position=new Point(x,y);
        this.inputPorts = inputPorts;
        this.outputPorts = outputPorts;
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public SystemType getSystemType() { return systemType; }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public List<PortDTO> getInputPorts() { return inputPorts; }
    public List<PortDTO> getOutputPorts() { return outputPorts; }
    public Point getPosition(){ return position; }
}
