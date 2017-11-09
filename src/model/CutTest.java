package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CutTest extends Renderer {
    public Point usecka1;
    public Point usecka2;

    private List<Point> pruseciky = new ArrayList<>();

    public CutTest(BufferedImage img) {
        super(img);
        usecka1 = new Point(0, img.getHeight() / 2);
        usecka2 = new Point(img.getWidth(), img.getHeight() / 2);
    }

    public void test(List<Point> p) {
        for (int i = 0; i < p.size(); i += 2) {
            Point bod1 = p.get(i);
            Point bod2 = p.get((i + 1) % p.size());

            double x1 = (int) usecka1.getX();
            double x2 = (int) usecka2.getX();
            double x3 = (int) bod1.getX();
            double x4 = (int) bod2.getX();

            double y1 = (int) usecka1.getY();
            double y2 = (int) usecka2.getY();
            double y3 = (int) bod1.getY();
            double y4 = (int) bod2.getY();

            double denum = ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
            if (denum == 0) return;

            double x = ((x1 * y2 - x2 * y1) * (x3 - x4) - (x3 * y4 - x4 * y3) * (x1 - x2)) / denum;
            double y = ((x1 * y2 - x2 * y1) * (y3 - y4) - (x3 * y4 - x4 * y3) * (y1 - y2)) / denum;

            //TODO Kontrola jestli jsou oba vertikální či horizontální

            //Kontrola jestli jsou paralelní takže se neprotínají takže mají stejnou směrnici
            double d1 = (y2 - y1) / (x2 - x1);
            double d2 = (y4 - y3) / (x4 - x3);
            if (d1 == d2) return;


            //Jestli x leží na dané úsečce x1 - x2 nebo x3 - x4
            if (Math.min(x1, x2) < x && x < Math.max(x1, x2) && Math.min(x3, x4) < x && x < Math.max(x3, x4)) {
                Point prusecik = new Point((int) x, (int) y);
                System.out.println("Prusečík: " + i + " X " + x + " Y " + y1);
                pruseciky.add(prusecik);
            }

        }

    }

    public void isInside(int x, int y) {
        Point p1 = new Point(10, 240);
        Point p2 = new Point(500, 240);

        int x1 = (int) p1.getX();
        int x2 = (int) p2.getX();
        int y1 = (int) p1.getY();
        int y2 = (int) p2.getY();

        float A = x - x1; // position of point rel one end of line
        float B = y - y1;
        float C = x2 - x1; // vector along line
        float D = y2 - y1;
        float E = -D; // orthogonal vector
        float F = C;

        float dot = A * E + B * F;
        float len_sq = E * E + F * F;

        //System.out.println(dot);

        //System.out.println((float) (dot / Math.sqrt(len_sq)));

        double dist = (x - x1) * (y1 - y2) + (y - y1) * (x2 - x1);
        System.out.println(dist);
    }

}
