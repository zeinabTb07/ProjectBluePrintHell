package model.enums;


public enum PortType {
    SQUARE(GameShape.SQUARE),
    TRIANGLE(GameShape.TRIANGLE);

    private final GameShape shape;

    PortType(GameShape shape) {
        this.shape = shape;
    }

    public GameShape getShape(){
        return this.shape;
    }
}

