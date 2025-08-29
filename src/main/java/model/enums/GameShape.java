package model.enums;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

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
            if (size<=5){
                size = 2*size;
            }
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
            Area lock = new Area();
            size = 20;
            int w = size;
            int h = (int)(w * 1.4);

            Rectangle2D body = new Rectangle2D.Double(
                    center.x - w/2.0,
                    center.y,
                    w,
                    h
            );



            Ellipse2D circle = new Ellipse2D.Double(
                    center.x - w*0.15,
                    center.y + h*0.3,
                    w*0.3,
                    w*0.3
            );
            Rectangle2D stem = new Rectangle2D.Double(
                    center.x - w*0.05,
                    center.y + h*0.5,
                    w*0.1,
                    h*0.25
            );

            lock.add(new Area(body));
            lock.subtract(new Area(circle));
            lock.subtract(new Area(stem));
            return lock;
        }
    },
    PHANTOM {
        @Override
        public Shape getShape(Point center, int size) {
            Random rnd = new Random();
            Path2D.Double combined = new Path2D.Double();

            int layers = 3;
            for (int l = 0; l < layers; l++) {
                int n = 7 + rnd.nextInt(6);
                double baseR = size * (0.35 + 0.2 * l + rnd.nextDouble() * 0.15);

                double ang = rnd.nextDouble() * Math.PI * 2;
                double step = (Math.PI * 2) / n;

                double firstX = 0, firstY = 0; // برای ذخیره اولین نقطه

                for (int i = 0; i < n; i++) {
                    double r = baseR * (0.7 + rnd.nextDouble() * 0.6);
                    double x = center.x + r * Math.cos(ang);
                    double y = center.y + r * Math.sin(ang);

                    if (i == 0) {
                        combined.moveTo(x, y);
                        firstX = x;
                        firstY = y;
                    } else {
                        combined.lineTo(x, y);
                    }

                    ang += step + (rnd.nextDouble() - 0.5) * step * 0.35;
                }

                combined.closePath();
            }
            return combined;
        }

    },
    SPIRIT {
        @Override
        public Shape getShape(Point center, int size) {
            Random rnd = new Random();
            Path2D.Double starLines = new Path2D.Double();

            int lines = 8;
            for (int i = 0; i < lines; i++) {
                double angle = rnd.nextDouble() * 2 * Math.PI;
                double length = size * (0.4 + rnd.nextDouble() * 0.5);
                double dx = Math.cos(angle) * length / 2.0;
                double dy = Math.sin(angle) * length / 2.0;

                double x1 = center.x - dx;
                double y1 = center.y - dy;
                double x2 = center.x + dx;
                double y2 = center.y + dy;

                starLines.moveTo(x1, y1);
                starLines.lineTo(x2, y2);
            }
            return starLines;
        }
    },

    TITAN {
        @Override
        public Shape getShape(Point center, int size) {
            Path2D.Double network = new Path2D.Double();

            int cols = 4;
            int rows = 4;

            double side = size / (cols * 1.5 + 0.5);
            double hexHeight = Math.sqrt(3) * side;

            double startX = center.x - size / 2.0 + side;
            double startY = center.y - (hexHeight * rows) / 2.0 + hexHeight / 2.0;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    double cx = startX + col * side * 1.5;
                    double cy = startY + row * hexHeight + (col % 2) * (hexHeight / 2.0);

                    Path2D.Double hex = new Path2D.Double();
                    for (int k = 0; k < 6; k++) {
                        double angle = Math.PI / 3 * k;
                        double x = cx + side * Math.cos(angle);
                        double y = cy + side * Math.sin(angle);
                        if (k == 0) {
                            hex.moveTo(x, y);
                        } else {
                            hex.lineTo(x, y);
                        }
                    }
                    hex.closePath();
                    network.append(hex, false);
                }
            }

            return network;
        }
    }

    ,RANGAROK {
        @Override
        public Shape getShape(Point center, int size) {
            Path2D.Double network = new Path2D.Double();

            int layers = 2;
            double radiusStep = size / (layers + 1);

            for (int layer = 0; layer <= layers; layer++) {
                int points = (layer == 0) ? 1 : layer * 6;
                double radius = layer * radiusStep;

                for (int i = 0; i < points; i++) {
                    double angle = 2 * Math.PI * i / points;
                    double x = center.x + radius * Math.cos(angle);
                    double y = center.y + radius * Math.sin(angle);

                    double r = (i % 2 == 0) ? radiusStep * 0.3 : radiusStep * 0.5;
                    Ellipse2D.Double circle =
                            new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
                    network.append(circle, false);
                }
            }

            return network;
        }
    }
    ;



    public abstract Shape getShape(Point center, int size);
}