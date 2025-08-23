package model.objects;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public abstract class GameObject implements Serializable {
    protected  UUID id ;
    protected Shape shape;
    protected boolean frozen;

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public GameObject(){
        this.id = UUID.randomUUID();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }



}
