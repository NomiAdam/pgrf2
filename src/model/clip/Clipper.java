package model.clip;

import model.helper.Edge;
import model.helper.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro orezani polygonu
 *
 * @author Adam Kvasnicka
 * @version 2017
 */
public class Clipper extends Renderer {
    private List<Point> clippingArea;

    public Clipper(BufferedImage img) {
        super(img);
    }

    /**
     * @param clippingArea Nastaveni orezavaciho polygonu
     */
    public void setClippingArea(List<Point> clippingArea) {
        this.clippingArea = clippingArea;
    }

    /**
     * Metoda pro orez polygonu
     * @param polygonIn vstupni polygon, ktery bude nasledne orezan
     * @return list bodu, ktery predstavuje orezany polygon
     */
    public List<Point> clip(List<Point> polygonIn) {
        List<Point> input;
        List<Point> out = polygonIn;
        List<Edge> clippingEdges = new ArrayList<>();

        for (int i = 0; i < clippingArea.size(); i++) {
            clippingEdges.add(new Edge(clippingArea.get(i), clippingArea.get((i + 1) % clippingArea.size())));
        }

        for (Edge edge : clippingEdges) {

            input = new ArrayList<>(out);
            out.clear();

            Point v1 = input.get(input.size() - 1);
            for (Point v2 : input) {
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
