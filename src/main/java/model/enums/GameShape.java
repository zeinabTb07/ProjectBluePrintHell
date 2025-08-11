package model.enums;

import java.awt.*;

public enum GameShape {
    SQUARE {
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();
            polygon.addPoint(center.x - size, center.y - size);
            polygon.addPoint(center.x + size, center.y - size);
            polygon.addPoint(center.x + size, center.y + size);
            polygon.addPoint(center.x - size, center.y + size);
            return polygon;
        }
    },
    TRIANGLE {
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();
            polygon.addPoint(center.x, center.y - size);
            polygon.addPoint(center.x + size, center.y + size);
            polygon.addPoint(center.x - size, center.y + size);
            return polygon;
        }
    };

    public abstract Shape getShape(Point center, int size);
}