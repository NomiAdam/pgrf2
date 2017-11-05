package model;

import java.awt.*;

public class Edge {
    private final Point a;
    private final Point b;

    public Edge(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public boolean isHorizontal() {
        return (a.getY() == b.getY());
    }

    public boolean isIntersection(int y) {
        return (a.getY() <= y) && (b.getY() > y);
    }

    public Edge getChangedDirectionIfNecessary() {
        if (a.getY() > b.getY()) {
            return new Edge(b, a);
        } else {
            return this;
        }
    }

    public int getIntersection(int y) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();

        double k = dx / dy;
        double q = a.getX() - (k * a.getY());

        int x = (int) (k * y + q);

        return x;
    }

}
