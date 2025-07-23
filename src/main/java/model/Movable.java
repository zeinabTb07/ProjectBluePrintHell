package model;

public interface Movable {
    void moveNormal(Packet packet);
    void moveInduced(Vector2D force , Packet packet);
}