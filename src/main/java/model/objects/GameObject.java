package model.objects;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {
    protected  UUID id ;
    protected Shape shape;

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
