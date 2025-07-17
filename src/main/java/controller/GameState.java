package controller;

import model.*;
import view.FrameManager;
import view.InfoBar;
import view.SystemRender;

import java.util.ArrayList;

public class GameState {

    private static GameState instance = new GameState(new Stage1(3500 , 10));

    private Stage gameStage ;
    private ArrayList<InputPort> inputPorts ;
    private ArrayList<OutputPort> outputPorts ;
    private ArrayList<Connection> connections ;
    private ArrayList<Packet> packets ;

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    private GameState(Stage gameStage) {
        this.gameStage = gameStage;
        this.inputPorts = new ArrayList<>();
        this.packets = new ArrayList<>();
        this.outputPorts = new ArrayList<>();
        this.connections = new ArrayList<>();
        for(G_System system : gameStage.getSystems()){
            if(system instanceof ReferenceSystem){
                for(Packet packet : ((ReferenceSystem) system).getPackets()){
                    packets.add(packet);
                }
            }
        }
        initializePorts();
    }

    public static void initialize(Stage gameStage) {
        if (instance == null) {
            instance = new GameState(gameStage);
        }
    }

    public static GameState getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GameState not initialized. Call initialize() first.");
        }
        return instance;
    }


    public void changeStage(Stage newStage) {
        instance = new GameState(newStage);
    }

    public ArrayList<G_System> getSystems() {
        return gameStage.getSystems();
    }

    public void addConnetion(Connection connection){
        connections.add(connection);
        FrameManager.musicPlayer.playSoundEffect("src/main/resources/connect.wav");
    }

    public void removeConnetion(Connection connection){
        connections.remove(connection);
        FrameManager.musicPlayer.playSoundEffect("src/main/resources/error.wav");
        connection.disconnect();
    }

    public boolean hasSolution() {
        return inputPorts.size() == outputPorts.size();
    }

    private void initializePorts() {
        for (G_System system : gameStage.getSystems()) {
            addPorts(system);
        }
    }

    private void addPorts(G_System system) {
        inputPorts.addAll(system.getInputs());
        outputPorts.addAll(system.getOutput());
    }

    public ArrayList<InputPort> getInputPorts(){
        return inputPorts;
    }

    public ArrayList<OutputPort> getOutputPorts(){
        return outputPorts;
    }

    public ArrayList<Connection> getConnections(){
        return connections;
    }

    public Stage getGameStage(){
        return gameStage;
    }
}