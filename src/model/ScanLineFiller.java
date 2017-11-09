package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLineFiller extends Renderer {
    private List<Edge> edges = new ArrayList<>();
    private List<Integer> intersections = new ArrayList<>();

    //TODO Barva vyplne je jina než hranice

    public ScanLineFiller(BufferedImage img) {
        super(img);
    }

    public void draw(List<Point> p) {
        int yMax = (int) p.get(0).getY();
        int yMin = (int) p.get(0).getY();

        for (int i = 0; i < p.size(); i++) {
            Point a = p.get(i);
            Point b = p.get((i + 1) % p.size());

            if (a.getY() > yMax) {
                yMax = (int) a.getY();
            } else if (a.getY() < yMin) {
                yMin = (int) a.getY();
            }

            Edge edge = new Edge(a, b);

            if (!edge.isHorizontal()) {
                edge = edge.getChangedDirectionIfNecessary();
                edges.add(edge);
            }
        }

        for (int y = yMin; y < yMax; y++) {
            for (Edge e : edges) {
                if (e.isIntersection(y)) {
                    intersections.add(e.getIntersection(y));
                }
            }

            //TODO make my own collection - bubble or better
            Collections.sort(intersections);

            LineRenderer line = new LineRenderer(img);
            for (int i = 0; i < intersections.size(); i += 2) {
                line.setColor(0xff0000);
                line.draw(intersections.get(i), intersections.get((i + 1) % intersections.size()), y, y);
            }
            intersections.clear();
        }
        PolygonRenderer poly = new PolygonRenderer(img);
        poly.drawPolygon(p);
    }
}
