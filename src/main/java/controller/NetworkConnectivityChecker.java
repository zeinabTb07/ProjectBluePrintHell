package controller;

import model.objects.systems.InputPort;
import model.objects.systems.NetworkSystem;
import model.objects.systems.OutputPort;

import java.util.*;

public class NetworkConnectivityChecker {
    private final List<NetworkSystem> systems;

    public NetworkConnectivityChecker(List<NetworkSystem> systems) {
        this.systems = new ArrayList<>(systems);
    }
    public boolean check(){
        System.out.println("ports : "+checkAllPortConnected());
        System.out.println("condoctivity : "+ isConnected());
        return isConnected() && checkAllPortConnected();
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
            for(ArrayList<InputPort> ports : system.getInputPorts().values()){
                for(InputPort port: ports){
                    if(!port.isConnected()) return false;
                }
            }
            for(ArrayList<OutputPort> ports : system.getOutputPorts().values()){
                for(OutputPort port: ports){
                    if(!port.isConnected()) return false;
                }
            }
        }
        return true;
    }
}