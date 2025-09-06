package utils;

import model.objects.systems.addon.InputPort;
import model.objects.systems.NetworkSystem;
import model.objects.systems.addon.OutputPort;

import java.util.*;

public class NetworkConnectivityChecker {
    private final List<NetworkSystem> systems;

    public NetworkConnectivityChecker(List<NetworkSystem> systems) {
        this.systems = systems;
    }
    public boolean check(){
        return isConnected() ;
    }
    private boolean isConnected() {
        if (systems.isEmpty()) {
            return true;
        }

        Set<NetworkSystem> visited = new HashSet<>();
        Queue<NetworkSystem> queue = new LinkedList<>();

        NetworkSystem start = systems.get(0);
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            NetworkSystem current = queue.poll();
            Set<NetworkSystem> neighbors = current.getNeighbors();

            for (NetworkSystem neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == systems.size();
    }

    private boolean checkAllPortConnected(){
        for(NetworkSystem system: systems){
            for(InputPort port: system.getInputPorts()){
                if(!port.isConnected()) return false;
            }
            for(OutputPort port: system.getOutputPorts()){
                if(!port.isConnected()) return false;
            }
        }
        return true;
    }
}