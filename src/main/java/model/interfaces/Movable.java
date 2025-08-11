package model.interfaces;

import model.Vector2D;

public interface Movable {
    void moveNormal(int deltaTime);
    void moveInduced(Vector2D vector2D);
}