package model.enums;

import java.awt.*;
import java.awt.geom.Point2D;

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
    },
    BITE {
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();
            double angleStep = Math.PI / 3;


            int leftCenterX = center.x + size/2;
            int leftCenterY = center.y - size/2;
            int rightCenterX = center.x - size/2;
            int rightCenterY = center.y + size/2;



            for (int i = 6; i >=0 ; i--) {
                double angle = angleStep * (i);
                int x = (int) (leftCenterX + size * Math.sin(angle));
                int y = (int) (leftCenterY + size * Math.cos(angle));
                polygon.addPoint(x, y);
            }

            for (int i = 6; i >=0 ; i--) {
                double angle = -angleStep * i;
                int x = (int) (rightCenterX + size * Math.sin(angle));
                int y = (int) (rightCenterY + size * Math.cos(angle));
                polygon.addPoint(x, y);
            }

            return polygon;
        }
    },
    LOCK {
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();

            return polygon;
        }
    },
    PHANTOM{
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();

            return polygon;
        }
    } ,
    SPIRIT{
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();

            return polygon;
        }
    } ,
    TITAN{
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();

            return polygon;
        }
    } , RANGAROK{
        @Override
        public Shape getShape(Point center, int size) {
            Polygon polygon = new Polygon();

            return polygon;
        }
    };



    public abstract Shape getShape(Point center, int size);
}