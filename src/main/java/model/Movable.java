package model;

public interface Movable {
    void setPosition(int x, int y);
    boolean canMove(int newX, int newY);
}