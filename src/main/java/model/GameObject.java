package model;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {
    protected  UUID id ;

    public abstract Point getPosition();
    public abstract void update();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
