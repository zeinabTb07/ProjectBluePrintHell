package model.interfaces;

import model.constants.Vector2D;

public interface Forceable {
    void moveInduced(Vector2D forceVector);
}