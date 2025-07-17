package controller;

import model.G_System;
import model.ReferenceSystem;

import java.util.ArrayList;

public class Stage {
    private ArrayList<G_System> systems ;
    private int wireLength ;
    private int stageTime ;

    public Stage(int wireLength , int stageTime){
        this.wireLength = wireLength ;
        this.stageTime = stageTime ;
        systems = new ArrayList<>();
    }

    public  void  addSystem(G_System system){
        systems.add(system);
    }

    public void removeSystem(G_System system){
        if (systems.contains(system)){
            systems.remove(system);
        }
    }

    public int getStageTime() {
        return stageTime;
    }


    public int getWireLength() {
        return wireLength;
    }

    public ArrayList<G_System> getSystems(){
        return systems;
    }


    public void setWireLength(int wireLength){
        this.wireLength = wireLength;
    }

    public void setStageTime(int stageTime){
        this.stageTime = stageTime;
    }

    private int  calculateTotalPacket (){
        int n = 0 ;
        for(G_System system : systems){
            if(system instanceof ReferenceSystem){
                n+=((ReferenceSystem) system).getPackets().size();
            }
        }
        return n ;
    }
}
