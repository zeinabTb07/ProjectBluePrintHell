package model;

public class Inductor {
    private NetworkSystem system ;
    private Boolean onOrOff ;

    protected Inductor(NetworkSystem system){
        this.system = system ;
        onOrOff = false ;
    }

    public NetworkSystem getSystem() {
        return system;
    }

    public boolean getConnection(){
        return onOrOff;
    }

    public void setConnection(Boolean onOrOff){
        this.onOrOff = onOrOff;
    }


}