package model.constants;

public class PacketRecord {
    public static record PacketProperties(int coin, int size) {}
    public static record PacketMovement(int speed , int acceleration){
    }

}
