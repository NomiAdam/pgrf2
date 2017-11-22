package model.filler;

import model.helper.Edge;
import model.helper.Renderer;
import model.render.LineRenderer;
import model.render.PolygonRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro vyplneni polygonu pomoci algoritmu Scan-Line
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class ScanLineFiller extends Renderer {
    private LineRenderer lr;

    public ScanLineFiller(BufferedImage img) {
        super(img);
        lr = new LineRenderer(img);
        lr.setColor(0xffffff);
    }

    public ScanLineFiller(BufferedImage img, int color) {
        super(img);
        lr = new LineRenderer(img);
        lr.setColor(color);
    }

    public void setColor(int color) {
        lr.setColor(color);
    }

    /**
     * Metoda pro vyplneni polygonu pomoci algoritmu Scan-Line
     *
     * @param p vstupni polygon ktery budeme vyplnovat
     */
    public void draw(List<Point> p) {
        int yMax = (int) p.get(0).getY();
        int yMin = (int) p.get(0).getY();

        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < p.size(); i++) {
            Point a = p.get(i);
            Point b = p.get((i + 1) % p.size());

            if (a.getY() > yMax) {
                yMax = (int) a.getY();
            } else if (a.getY() < yMin) {
                yMin = (int) a.getY();
            }

            Edge edge = new Edge(a, b);

            //Kontrola jestli hrana neni horizontalni, pokud ano vynecháme
            if (!edge.isHorizontal()) {
                edge.getChangedDirectionIfNecessary();
                edges.add(edge);
            }
        }

        List<Integer> intersections = new ArrayList<>();

        for (int y = yMin; y < yMax; y++) {
            for (Edge e : edges) {
                if (e.isIntersection(y)) {
                    intersections.add(e.getIntersection(y));
                }
            }

            //Setrizeni bodu
            goodOldBubbleSort(intersections);

            for (int i = 0; i < intersections.size(); i += 2) {
                lr.draw(intersections.get(i), intersections.get((i + 1) % intersections.size()), y, y);
            }
            intersections.clear();
        }
        PolygonRenderer poly = new PolygonRenderer(img);
        poly.drawPolygon(p);
    }

    public void goodOldBubbleSort(List<Integer> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = 0; j < points.size() - 1; j++) {
                Integer a = points.get(j);
                Integer b = points.get(j + 1);
                if (a.compareTo(b) > 0) {
                    points.set(j, b);
                    points.set(j + 1, a);
                }
            }
        }
    }
}
