package view;


import java.awt.*;

public class Rectangle extends Polygon {
    private int a ;
    private int b ;
    private int centerX ;
    private int centerY ;
    private Color color;
    public Rectangle (int centerX, int centerY, int a, int b , Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.a = a;
        this.b = b;
        this.color = color;
        updateRectangle(centerX , centerY );
    }

    private void updateRectangle(int X, int Y) {
        int halfA = a / 2;
        int halfB = b / 2;

        this.centerX = X;
        this.centerY = Y;
        int topLeftX = centerX - halfA;
        int topLeftY = centerY - halfB;

        int topRightX = centerX + halfA;
        int topRightY = centerY - halfB;

        int bottomRightX = centerX + halfA;
        int bottomRightY = centerY + halfB;

        int bottomLeftX = centerX - halfA;
        int bottomLeftY = centerY + halfB;

        reset();
        addPoint(topLeftX, topLeftY);
        addPoint(topRightX, topRightY);
        addPoint(bottomRightX, bottomRightY);
        addPoint(bottomLeftX, bottomLeftY);
    }
    
    public Point getLocation() {
        return new Point(centerX , centerY);
    }

    public int getSize(){
        return (int) Math.sqrt(a*a+b*b)/2;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(this);
    }
}