package model.constants;

import java.awt.*;

public class Line {
   public Point start, end;
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    public void setEnd(Point end) {
        this.end = end;
    }
    public Point getEnd() {
        return end;
    }
}