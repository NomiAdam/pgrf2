package model;

import java.awt.*;
import java.util.List;

public class Edge {
    private Point a;
    private Point b;

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

    public void getChangedDirectionIfNecessary() {
        if (a.getY() > b.getY()) {
            Point pomoc = b;
            b = a;
            a = pomoc;
        }
    }

    public int getIntersection(int y) {
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();

        double k = dx / dy;
        double q = a.getX() - (k * a.getY());

        int x = (int) Math.round((k * y + q) + 0.5);

        return x;
    }

    public boolean isInside(Point p) {
        int x = (int) p.getX();
        int y = (int) p.getY();

        int x1 = (int) a.getX();
        int x2 = (int) b.getX();
        int y1 = (int) a.getY();
        int y2 = (int) b.getY();

        //Zkontrolovat jestli je horizontal nevo vertical a pak prohodit nerovnost

        float A = x - x1; // position of point rel one end of line
        float B = y - y1;
        float C = x2 - x1; // vector along line
        float D = y2 - y1;
        float E = -D; // orthogonal vector
        float F = C;

        float dot = A * E + B * F;

        //Dont change
        return dot <= 0;
    }

    public Point intersection(Point p1, Point p2) {

        double x1 = a.getX();
        double x2 = b.getX();
        double x3 = p1.getX();
        double x4 = p2.getX();

        double y1 = a.getY();
        double y2 = b.getY();
        double y3 = p1.getY();
        double y4 = p2.getY();

        double denum = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        if (denum == 0)
            return null;

        double x = ((x1 * y2 - x2 * y1) * (x3 - x4) - (x3 * y4 - x4 * y3) * (x1 - x2)) / denum;
        double y = ((x1 * y2 - x2 * y1) * (y3 - y4) - (x3 * y4 - x4 * y3) * (y1 - y2)) / denum;


        //Kontrola jestli jsou paralelní takže se neprotínají takže mají stejnou směrnici
        double d1 = (y2 - y1) / (x2 - x1);
        double d2 = (y4 - y3) / (x4 - x3);
        if (d1 == d2) return null;

        return new Point((int) x, (int) y);
    }
}

