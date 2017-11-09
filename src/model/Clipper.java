package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Clipper extends Renderer {
    private List<Point> clippingArea;

    public Clipper(BufferedImage img) {
        super(img);
    }

    public void setClippingArea(List<Point> clippingArea) {
        this.clippingArea = clippingArea;
    }

    public List<Point> clip(List<Point> polygonIn) {
        List<Point> out = new ArrayList<>();
        List<Edge> clippingEdges = new ArrayList<>();

        for (int i = 0; i < clippingArea.size(); i++) {
            clippingEdges.add(new Edge(clippingArea.get(i), clippingArea.get((i + 1) % clippingArea.size())));
        }

        for (Edge edge : clippingEdges) {
            out.clear();
            Point v1 = polygonIn.get(polygonIn.size() - 1);
            for (Point v2 : polygonIn) {
                if (edge.isInside(v2)) {
                    if (!edge.isInside(v1))
                        out.add(edge.intersection(v1, v2));
                    out.add(v2);
                } else {
                    if (edge.isInside(v1))
                        out.add(edge.intersection(v1, v2));
                }
                v1 = v2;
            }
        }

        return out;
    }


}
