package model.constants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Path2D;
import java.util.List;

public class GeometryUtils {

    public static double calcDistance(List<Point> points) {
        double dist = 0;
        for (int i = 1; i < points.size(); i++) {
            dist += points.get(i - 1).distance(points.get(i));
        }
        return dist;
    }

    public static Point getRelativePoint(Path2D path, double t) {
        // فرض: t بین 0 و 1
        double totalLen = calcPathLength(path);
        return getPointAtDistance(path, totalLen * t);
    }

    public static Point getPointAtDistance(Path2D path, double dist) {
        double lenSoFar = 0;
        int[] coords = new int[6];
        Point prev = null;
        for (var it = path.getPathIterator(null, 0.5); !it.isDone(); it.next()) {
            Point curr = new Point(coords[0], coords[1]);
            if (prev != null) {
                double segLen = prev.distance(curr);
                if (lenSoFar + segLen >= dist) {
                    double ratio = (dist - lenSoFar) / segLen;
                    return new Point(
                            (int) (prev.getX() + ratio * (curr.getX() - prev.getX())),
                            (int)(prev.getY() + ratio * (curr.getY() - prev.getY()))
                    );
                }
                lenSoFar += segLen;
            }
            prev = curr;
        }
        return prev;
    }

    public static double calcPathLength(Path2D path) {
        double len = 0;
        double[] coords = new double[6];
        Point2D prev = null;
        for (var it = path.getPathIterator(null, 0.5); !it.isDone(); it.next()) {
            int segType = it.currentSegment(coords);
            Point2D curr = new Point2D.Double(coords[0], coords[1]);
            if (prev != null) len += prev.distance(curr);
            prev = curr;
        }
        return len;
    }

    public static Point getUnitTangent(Path2D path, double dist) {
        Point2D p1 = getPointAtDistance(path, dist);
        Point2D p2 = getPointAtDistance(path, dist + 1e-3);
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double mag = Math.sqrt(dx * dx + dy * dy);
        return new Point((int) (dx / mag), (int) (dy / mag));
    }
}
