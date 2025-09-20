package shared.model.objects;

import java.io.Serializable;
import java.util.UUID;

public abstract class GameObject implements Serializable {
    protected  UUID id ;


    public GameObject(){
        this.id = UUID.randomUUID();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
