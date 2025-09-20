package shared.model.objects.systems.addon;

import shared.model.objects.GameObject;
import shared.model.objects.systems.NetworkSystem;

import java.io.Serializable;

public class Inductor extends GameObject implements Serializable {
    protected NetworkSystem system ;

    public Inductor(NetworkSystem system){
        this.system = system ;
        super.id = system.getId();
    }

    public boolean checkConnections() {
        boolean inputsConnected = system.getInputPorts().isEmpty() ||
                system.getInputPorts().stream()
                        .anyMatch(port -> port.getConnection() != null);

        boolean outputsConnected = system.getOutputPorts().isEmpty() ||
                system.getOutputPorts().stream()
                        .anyMatch(port -> port.getConnection() != null);

        return inputsConnected && outputsConnected;
    }


    public NetworkSystem getSystem() {
        return system;
    }

    public void setSystem(NetworkSystem system) {
        this.system = system;
    }
}