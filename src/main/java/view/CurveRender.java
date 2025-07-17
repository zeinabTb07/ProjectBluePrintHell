package view;

import model.Curve;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;


public class CurveRender {
    private QuadCurve2D q ;
   public CurveRender(){}

    public void paint(Graphics2D g, Curve curve) {
        g.setColor(Color.RED);
        g.setStroke(curve.getStroke());

        for(QuadCurve2D q : getQuadratic(curve)){
            g.draw(q);
        }
    }

    public ArrayList<QuadCurve2D> getQuadratic(Curve curve){

        List<Point> points = curve.getPoints();
        ArrayList<QuadCurve2D> quadCurve2DS = new ArrayList<>();
        for (int i = 0; i <= points.size() - 3; i += 2) {
            Point start = points.get(i);
            Point control = points.get(i + 1);
            Point end = points.get(i + 2);


            q = new QuadCurve2D.Double(
                    start.getX(), start.getY(),
                    control.getX(), control.getY(),
                    end.getX(), end.getY()
            );

            quadCurve2DS.add(q);
        }

        return quadCurve2DS;
    }

}
