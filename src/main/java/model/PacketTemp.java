package model;

public class PacketTemp {}
//    private final double V = 2;
//    private final double a = 0.0000000002;
//    private double speed;
//    private double acceleration;
//    private int size;
//    private int reward;
//    private Type type;
//    private final ReferenceSystem originSystem;
//    private G_System currentSystem;
//    private OutputPort sourcePort;
//    private int x;
//    private int y;
//
//    public Packet(ReferenceSystem origin, Type type) {
//        this.originSystem = origin;
//        this.currentSystem = origin;
//        this.type = type;
//
//        if(type==Type.Rectangle){
//            reward = 1;
//            size = 2;
//        }
//        if(type==Type.Triangle){
//            reward = 2;
//            size = 3;
//        }
//        x = originSystem.x + origin.getWidth()/2 ;
//        y = originSystem.y + origin.getHeight()/2 + 40;
//        this.setInitialSourcePort();
//    }
//    private void setInitialSourcePort() {
//        if(this.originSystem != null && !this.originSystem.getOutput().isEmpty()) {
//            this.sourcePort = this.originSystem.getOutput().getFirst();
//        }
//    }
//
//    public double getV() {
//        return V;
//    }
//
//    public double getA() {
//        return a;
//    }
//
//    public double getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(double speed) {
//        this.speed = speed;
//    }
//
//    public double getAcceleration() {
//        return acceleration;
//    }
//
//    public void setAcceleration(double acceleration) {
//        this.acceleration = acceleration;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }
//
//    public int getReward() {
//        return reward;
//    }
//
//    public void setReward(int reward) {
//        this.reward = reward;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    public ReferenceSystem getOriginSystem() {
//        return originSystem;
//    }
//
//    public G_System getCurrentSystem() {
//        return currentSystem;
//    }
//
//    public void setCurrentSystem(G_System currentSystem) {
//        this.currentSystem = currentSystem;
//    }
//
//    public OutputPort getSourcePort() {
//        return sourcePort;
//    }
//
//    public void setSourcePort(OutputPort sourcePort) {
//        this.sourcePort = sourcePort;
//    }
//
//    public int getX() {
//        return x;
//    }
//
//    public int getY() {
//        return y;
//    }
//    public void setX(int x) {
//        this.x=x;
//    }
//
//    public void setY(int y) {
//        this.y = y ;
//    }
//
//    public void transferTo(G_System newSystem, OutputPort fromPort) {
//        this.currentSystem = newSystem;
//        this.sourcePort = fromPort;
//        setX(getSourcePort().getX());
//        setY(getSourcePort().getY());
//    }
//
//    public void setPacketSpeed() {
//        if(sourcePort.getPortType()==type) {
//            compatiblePort();
//        } else {
//            incompatiblePort();
//        }
//    }
//
//    public void compatiblePort() {
//        if(type==Type.Rectangle) {
//            acceleration = 0;
//            speed = V;
//        }
//        if(type==Type.Triangle) {
//            acceleration = 0;
//            speed = V;
//        }
//    }
//
//    public void incompatiblePort() {
//        if(type==Type.Rectangle) {
//            acceleration = 0;
//            speed = 2*V;
//        }
//        if(type==Type.Triangle) {
//            acceleration = a;
//            speed = V;
//        }
//    }
//
//    @Override
//    public void setPosition(int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
//
//    @Override
//    public boolean canMove(int newX, int newY) {
//        return false;
//    }
//    public void move() {
//
//        int newX = x + (int)(speed * Math.cos(getDirectionAngle()));
//        int newY = y + (int)(speed * Math.sin(getDirectionAngle()));
//
//
//        if (canMove(newX, newY)) {
//            setPosition(newX, newY);
//            speed += acceleration;
//        }
//
//    }
//
//    private double getDirectionAngle() {
//
//        if (sourcePort != null && sourcePort.isConnected()) {
//            InputPort target = getConnectedTo();
//            int targetX = target.getX() + target.getParentSystem().getX();
//            int targetY = target.getY() + target.getParentSystem().getY();
//            return Math.atan2(targetY - y, targetX - x);
//        }
//        return 0;
//    }
//
//    private InputPort getConnectedTo(){
//        for(Connection connection : GameState.getInstance().getConnections()){
//            if(sourcePort==connection.getSource()){
//                return connection.getTarget();
//            }
//        }
//         return null ;
//    }
//}
