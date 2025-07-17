package view;

import java.awt.*;


public class Triangle extends Polygon {
    private int size;
    private int centerX ;
    private int centerY ;
    private  Color color ;
    public Triangle (int centerX, int centerY , int size , Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.size = size;
        this.color = color;
        updateTriangle(centerX , centerY);
    }


    private void updateTriangle(int X, int Y) {
        this.centerX = X;
        this.centerY = Y;

        int vertex1X = centerX;
        int vertex1Y = centerY - size;

        int vertex2X = centerX - size;
        int vertex2Y = centerY + size;

        int vertex3X = centerX + size;
        int vertex3Y = centerY + size;

        reset();
        addPoint(vertex1X, vertex1Y);
        addPoint(vertex2X, vertex2Y);
        addPoint(vertex3X, vertex3Y);
    }

    public Point getLocation() {
        return new Point(centerX , centerY);
    }

    public int getSize(){
        return size;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(this);
    }
}
